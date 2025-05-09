package com.core.carryOn.Weather.Controller;

import com.core.carryOn.Weather.Service.WeatherService;
import com.core.carryOn.Weather.domain.TodayRainPercentage;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WeatherController {
    private final WeatherService weatherService;
    private final MemberService memberService;

    @GetMapping("weather")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public TodayRainPercentage getTodayRainPercentage() {
        Member member = memberService.getMyMemberWithAuthority().orElseThrow(NoSuchElementException::new);

        return weatherService.getTodayRainPercentage(member.getId())
                .orElseThrow(NoSuchElementException::new);
    }
}
