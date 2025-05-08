package com.core.carryOn.Cctv.Service;

import com.core.carryOn.Cctv.Dto.CctvDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CctvService {

    private final RestTemplate restTemplate;

    public CctvService() {
        this.restTemplate = new RestTemplate();
    }

    public List<CctvDto> getCctvList(double minX, double minY, double maxX, double maxY){
        String url = "http://www.utic.go.kr/map/mapcctv.do";
        String referer = "http://www.utic.go.kr/map/map.do?menu=incident";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("MIN_X", String.valueOf(minX));
        formData.add("MIN_Y", String.valueOf(minY));
        formData.add("MAX_X", String.valueOf(maxX));
        formData.add("MAX_Y", String.valueOf(maxY));

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Referer", referer);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // 요청 구성
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<List<CctvDto>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<CctvDto>>() {}
        );

        return response.getBody();
    }
}
