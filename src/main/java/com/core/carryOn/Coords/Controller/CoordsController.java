package com.core.carryOn.Coords.Controller;

import com.core.carryOn.Coords.Service.CoordsService;
import com.core.carryOn.Coords.domain.Coords;
import com.core.carryOn.Location.Service.LocationService;
import com.core.carryOn.Location.domain.Location;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CoordsController {
    private final MemberService memberService;
    private final LocationService locationService;
    private final CoordsService coordsService;

    @Autowired
    public CoordsController(MemberService memberService, LocationService locationService, CoordsService coordsService) {
        this.memberService = memberService;
        this.locationService = locationService;
        this.coordsService = coordsService;
    }


    @GetMapping("/my-coords")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Coords>> getMyCoords() {
        Member member = memberService.getMyMemberWithAuthority()
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));

        Location location = locationService.findByMemberId(member.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 멤버의 Location 정보가 없습니다."));

        Coords coords = coordsService.findByLocationId(location.getId().toHexString())
                .orElseThrow(() -> new NoSuchElementException("해당 Location에 대한 Coords 정보가 없습니다."));

        return ResponseEntity.ok(Collections.singletonList(coords));
    }

    @PostMapping("/setting-coords")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Coords> updateCoords(@RequestBody Coords coords) {
        Member member = memberService.getMyMemberWithAuthority()
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));

        Location location = locationService.findByMemberId(member.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 멤버의 Location 정보가 없습니다."));

        Optional<Coords> byLocationId = coordsService.findByLocationId(location.getId().toHexString());

        if (byLocationId.isPresent()) {
            Coords existedCoords = byLocationId.get();
            existedCoords.setSourceLatitude(coords.getSourceLatitude());
            existedCoords.setSourceLongitude(coords.getSourceLongitude());
            existedCoords.setDestLatitude(coords.getDestLatitude());
            existedCoords.setDestLongitude(coords.getDestLongitude());
            existedCoords.setTotalPathCoords(coords.getTotalPathCoords());
            existedCoords.setLocationId(location.getId().toHexString());
            coordsService.save(existedCoords);
        } else{
            Coords newCoords = new Coords();
            newCoords.setSourceLatitude(coords.getSourceLatitude());
            newCoords.setSourceLongitude(coords.getSourceLongitude());
            newCoords.setDestLatitude(coords.getDestLatitude());
            newCoords.setDestLongitude(coords.getDestLongitude());
            newCoords.setTotalPathCoords(coords.getTotalPathCoords());
            newCoords.setLocationId(location.getId().toHexString());
            coordsService.save(newCoords);
        }

        return ResponseEntity.ok(coords);
    }



}
