package com.core.carryOn.Location.Controller;

import com.core.carryOn.Location.Service.LocationService;
import com.core.carryOn.Location.domain.Location;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {
    MemberService memberService;
    LocationService locationService;

    @Autowired
    public LocationController(MemberService memberService, LocationService locationService) {
        this.memberService = memberService;
        this.locationService = locationService;
    }

    @GetMapping("/my-location")
    public Location getMyLocation(String email) {
        Member member = memberService.findOneByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("해당 이메일로 시작하는 멤버가 없습니다."));

        return locationService.findByMemberId(member.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 멤버의 위치정보가 없습니다."));
    }

    @PostMapping("/setting-location")
    public void updateMyLocation(@RequestBody Location location) {
        Optional<Location> optionalLocation = locationService.findByMemberId(location.getId());

        if (optionalLocation.isPresent()) {
            Location existMyLocation = optionalLocation.get();
            existMyLocation.setDestAddress(location.getDestAddress());
            existMyLocation.setDestSearched(location.getDestSearched());
            existMyLocation.setSourceAddress(location.getSourceAddress());
            existMyLocation.setSourceSearched(location.getSourceSearched());
            locationService.save(existMyLocation);
        } else{
            Location newLocation = new Location();
            newLocation.setDestAddress(location.getDestAddress());
            newLocation.setDestSearched(location.getDestSearched());
            newLocation.setSourceAddress(location.getSourceAddress());
            newLocation.setSourceSearched(location.getSourceSearched());
            newLocation.setMemberId(location.getMemberId());
            locationService.save(newLocation);
        }
    }
}
