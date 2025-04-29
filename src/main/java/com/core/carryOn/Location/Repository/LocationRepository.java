package com.core.carryOn.Location.Repository;

import com.core.carryOn.Location.domain.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
    Optional<Location> findByMemberId(String memberId);
}
