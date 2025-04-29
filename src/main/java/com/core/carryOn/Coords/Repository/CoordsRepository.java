package com.core.carryOn.Coords.Repository;

import com.core.carryOn.Coords.domain.Coords;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordsRepository extends MongoRepository<Coords, String> {
    Optional<Coords> findByLocationId(String locationId);
}
