package com.core.carryOn.Weather.Service;

import com.core.carryOn.Weather.domain.TodayRainPercentage;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WeatherServiceTest {

    private final WeatherService weatherService;
    private final MemberService memberService;



    @Autowired
    public WeatherServiceTest(WeatherService weatherService, MemberService memberService) {
        this.weatherService = weatherService;
        this.memberService = memberService;
    }


    @Test
    @DisplayName("사용자 아이디를 통해 조회")
    public void findByMemberId(){
        Member member = memberService.findOneByEmail("dign1120@naver.com")
                .orElseThrow(() -> new NoSuchElementException("해당 사용자가 없습니다."));

        Optional<TodayRainPercentage> todayRainPercentage = weatherService.getTodayRainPercentage(member.getId());
        assertTrue(todayRainPercentage.isEmpty());
    }


    @Test
    @DisplayName("사용자 아이디를 통해 저장")
    public void saveByMemberId(){
        Member member = memberService.findOneByEmail("dign1120@naver.com")
                .orElseThrow(() -> new NoSuchElementException("해당 사용자가 없습니다."));

        TodayRainPercentage todayRainPercentage = new TodayRainPercentage();
        todayRainPercentage.setMemberId(member.getId());
        todayRainPercentage.setPercentage(30.0);

        weatherService.saveTodayRainPercentage(todayRainPercentage);
    }
}