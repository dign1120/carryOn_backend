package com.core.carryOn.Weather.Service;

import com.core.carryOn.Weather.Repository.WeatherRepository;
import com.core.carryOn.Weather.domain.TodayRainPercentage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public Optional<TodayRainPercentage> getTodayRainPercentage(String memberId) {
        return weatherRepository.findByMemberId(memberId);
    }

    public void saveTodayRainPercentage(TodayRainPercentage todayRainPercentage) {
        Optional<TodayRainPercentage> existing = weatherRepository.findByMemberId(todayRainPercentage.getMemberId());

        if (existing.isPresent()) {
            TodayRainPercentage existingDoc = existing.get();
            existingDoc.setPercentage(todayRainPercentage.getPercentage());
            weatherRepository.save(existingDoc); // update
        } else {
            weatherRepository.save(todayRainPercentage); // insert
        }
    }
}
