package com.core.carryOn.Weather.Scheduler;

import com.core.carryOn.Coords.Service.CoordsService;
import com.core.carryOn.Coords.domain.Coordinate;
import com.core.carryOn.Coords.domain.Coords;
import com.core.carryOn.Location.Service.LocationService;
import com.core.carryOn.Location.domain.Location;
import com.core.carryOn.Weather.Service.WeatherService;
import com.core.carryOn.Weather.Utils.GridConverter;
import com.core.carryOn.Weather.domain.TodayRainPercentage;
import com.core.carryOn.WorkStart.Service.WorkStartService;
import com.core.carryOn.WorkStart.domain.WorkStart;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TodayRainScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TodayRainScheduler.class);
    private final WorkStartService workStartService;
    private final LocationService locationService;
    private final CoordsService coordsService;
    private final GridConverter gridConverter;
    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TodayRainScheduler(WorkStartService workStartService, LocationService locationService, CoordsService coordsService, GridConverter gridConverter, WeatherService weatherService) {
        this.workStartService = workStartService;
        this.locationService = locationService;
        this.coordsService = coordsService;
        this.gridConverter = gridConverter;
        this.weatherService = weatherService;
        this.objectMapper = new ObjectMapper();
    }

    @Value("${weather.api.serviceKey}")
    private String weatherApiServiceKey;

    @Scheduled(cron = "0 * * * * *",  zone = "Asia/Seoul")
    public void getTodayRain() {
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        LocalTime targetTime = now.plusMinutes(5);
        Map<String, List<Coordinate>> userTotalCoordinatesMap = new HashMap<>();
        Map<String, List<GridConverter.LatXLngY>> userGridMap = new HashMap<>();

        logger.info("🌧️ 강수확률 스케줄러 실행됨 ⏰ 현재 시각: {}, 대상 출근/외출 시각: {}", now, targetTime);

        List<WorkStart> allWorkStart = workStartService.findAll();

        List<WorkStart> filteredWorkStartList = allWorkStart.stream()
                .filter(ws -> targetTime.equals(ws.getStartTime().toLocalTime().withSecond(0).withNano(0)))
                .toList();

        for (WorkStart workStart : filteredWorkStartList) {
            Optional<Location> optionalLocation = locationService.findByMemberId(workStart.getMemberId());

            if (optionalLocation.isEmpty()) {
                continue;
            }

            Optional<Coords> byLocationId = coordsService.findByLocationId(optionalLocation.get().getId().toHexString());
            if (byLocationId.isEmpty()) {
                continue;
            }

            List<Coordinate> totalPathCoords = byLocationId.get().getTotalPathCoords();
            userTotalCoordinatesMap.put(workStart.getMemberId(), totalPathCoords);
        }

        userTotalCoordinatesMap.forEach((memberId, coordinateList) -> {
            List<GridConverter.LatXLngY> gridList = coordinateList.stream()
                    .map(coords -> gridConverter.convertGRID_GPS(GridConverter.TO_GRID, coords.getLatitude(), coords.getLongitude()))
                    .distinct() // LatXLngY equals/hashCode 잘 정의돼 있으면 중복 제거됨
                    .collect(Collectors.toList());

            userGridMap.put(memberId, gridList);
        });

        userGridMap.forEach((memberId, gridList) -> {
            List<Double> rainValues = new ArrayList<>();

            for (GridConverter.LatXLngY coords : gridList) {
                String baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                String baseTime = getNearestBaseTime(); // 아래 함수 참고

                URI uri = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                        .queryParam("serviceKey", weatherApiServiceKey) // 인코딩 자동 처리됨
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 50)
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", baseDate)
                        .queryParam("base_time", baseTime)
                        .queryParam("nx", (int)coords.x)
                        .queryParam("ny", (int)coords.y)
                        .build(true) // <- 여기 true로 해야 인코딩 안깨짐
                        .toUri();

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Accept", "application/json"); // JSON 형식 응답 요청

                    // 3. HttpEntity에 헤더 추가
                    HttpEntity<String> entity = new HttpEntity<>(headers);

                    // 4. RestTemplate을 사용하여 API 호출 (exchange 사용)
                    RestTemplate restTemplate = new RestTemplate();
                    String response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();

                    // 5. JSON 파싱
                    JsonNode root = objectMapper.readTree(response);
                    JsonNode items = root.path("response").path("body").path("items").path("item");

                    for (JsonNode item : items) {
                        if ("POP".equals(item.get("category").asText())) {
                            double fcstValue = item.get("fcstValue").asDouble();
                            rainValues.add(fcstValue);
                            break; // POP 항목 하나만 있으면 됨
                        }
                    }
                } catch (Exception e) {
                    logger.error("📡 API 호출 실패 - memberId: {}, x: {}, y: {}, err: {}", memberId, coords.x, coords.y, e.getMessage());
                }
            }

            // 6. 평균값 계산 및 DB 저장
            if (!rainValues.isEmpty()) {
                double avgRain = rainValues.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                TodayRainPercentage newTodayPercentage = new TodayRainPercentage();
                newTodayPercentage.setPercentage(avgRain);
                newTodayPercentage.setMemberId(memberId);
                weatherService.saveTodayRainPercentage(newTodayPercentage);
            }
        });

        logger.info("🌧️ 강수확률 스케줄러 실행완료  - 강수확률 업데이트: {} 명", userGridMap.size());
    }

    private String getNearestBaseTime() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.of(2, 10))) return "2300";
        if (now.isBefore(LocalTime.of(5, 10))) return "0200";
        if (now.isBefore(LocalTime.of(8, 10))) return "0500";
        if (now.isBefore(LocalTime.of(11, 10))) return "0800";
        if (now.isBefore(LocalTime.of(14, 10))) return "1100";
        if (now.isBefore(LocalTime.of(17, 10))) return "1400";
        if (now.isBefore(LocalTime.of(20, 10))) return "1700";
        if (now.isBefore(LocalTime.of(23, 10))) return "2000";
        return "2300";
    }
}

