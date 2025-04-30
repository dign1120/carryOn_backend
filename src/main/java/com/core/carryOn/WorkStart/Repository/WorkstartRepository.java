package com.core.carryOn.WorkStart.Repository;

import com.core.carryOn.WorkStart.domain.WorkStart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkstartRepository extends MongoRepository<WorkStart, String> {
    Optional<WorkStart> findByMemberId(String memberId);
}
