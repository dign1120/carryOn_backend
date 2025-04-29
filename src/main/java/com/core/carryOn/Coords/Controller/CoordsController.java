package com.core.carryOn.Coords.Controller;

import com.core.carryOn.Coords.Service.CoordsService;
import com.core.carryOn.Coords.domain.Coords;
import com.core.carryOn.Location.Service.LocationService;
import com.core.carryOn.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coords")
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


}
