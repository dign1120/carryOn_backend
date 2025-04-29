package com.core.carryOn.Location.Service;

import com.core.carryOn.Location.Repository.LocationRepository;
import com.core.carryOn.Location.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Optional<Location> findByMemberId(String id) {
        return locationRepository.findByMemberId(id);
    }

    public void save(Location location) {
        locationRepository.save(location);
    }
}
