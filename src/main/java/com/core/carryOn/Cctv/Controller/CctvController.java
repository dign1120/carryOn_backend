package com.core.carryOn.Cctv.Controller;

import com.core.carryOn.Cctv.Dto.CctvDto;
import com.core.carryOn.Cctv.Dto.CoordsDto;
import com.core.carryOn.Cctv.Service.CctvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CctvController {
    private final CctvService cctvService;

    @PostMapping("/map-cctv")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<CctvDto> getMapCctv(@RequestBody CoordsDto coordsDto ){
        return cctvService.getCctvList(coordsDto.getMin_x(), coordsDto.getMin_y(), coordsDto.getMax_x(), coordsDto.getMax_y());
    }
}
