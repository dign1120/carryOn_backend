package com.core.carryOn.Coords.Service;

import com.core.carryOn.Coords.Repository.CoordsRepository;
import com.core.carryOn.Coords.domain.Coords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoordsService {
    private final CoordsRepository coordsRepository;

    @Autowired
    public CoordsService(CoordsRepository coordsRepository) {
        this.coordsRepository = coordsRepository;
    }

    public Optional<Coords> findByLocationId(String locationId) {
        return coordsRepository.findByLocationId(locationId);
    }
}
