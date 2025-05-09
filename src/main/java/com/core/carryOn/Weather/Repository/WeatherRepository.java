package com.core.carryOn.Weather.Repository;

import com.core.carryOn.Weather.domain.TodayRainPercentage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends MongoRepository<TodayRainPercentage, String> {
    Optional<TodayRainPercentage> findByMemberId(String memberId);
}
