package com.core.carryOn.Searched.Repository;

import com.core.carryOn.Searched.domain.RecentlySearched;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchedRepository extends MongoRepository<RecentlySearched, String> {
    Optional<List<RecentlySearched>> findByMemberId(String memberId);
    Optional<RecentlySearched> findByMemberIdAndAddress(String memberId, String address);
}
