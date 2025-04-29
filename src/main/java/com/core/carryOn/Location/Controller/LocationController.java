package com.core.carryOn.Location.Controller;

import com.core.carryOn.Location.Service.LocationService;
import com.core.carryOn.Location.domain.Location;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LocationController {
    MemberService memberService;
    LocationService locationService;

    @Autowired
    public LocationController(MemberService memberService, LocationService locationService) {
        this.memberService = memberService;
        this.locationService = locationService;
    }

    @GetMapping("/my-location")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Location getMyLocation() {
        Member member = memberService.getMyMemberWithAuthority().orElseThrow(NoSuchElementException::new);
        return locationService.findByMemberId(member.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 멤버의 위치정보가 없습니다."));
    }

    @PostMapping("/setting-location")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void updateMyLocation(@RequestBody Location location) {
        Member member = memberService.getMyMemberWithAuthority().orElseThrow(NoSuchElementException::new);
        Optional<Location> optionalLocation = locationService.findByMemberId(member.getId());

        if (optionalLocation.isPresent()) {
            Location existMyLocation = optionalLocation.get();
            existMyLocation.setDestAddress(location.getDestAddress());
            existMyLocation.setDestSearched(location.getDestSearched());
            existMyLocation.setSourceAddress(location.getSourceAddress());
            existMyLocation.setSourceSearched(location.getSourceSearched());
            existMyLocation.setMemberId(member.getId());
            locationService.save(existMyLocation);
        } else{
            Location newLocation = new Location();
            newLocation.setDestAddress(location.getDestAddress());
            newLocation.setDestSearched(location.getDestSearched());
            newLocation.setSourceAddress(location.getSourceAddress());
            newLocation.setSourceSearched(location.getSourceSearched());
            newLocation.setMemberId(member.getId());
            locationService.save(newLocation);
        }
    }
}
