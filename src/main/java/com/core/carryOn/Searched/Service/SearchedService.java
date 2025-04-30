package com.core.carryOn.Searched.Service;

import com.core.carryOn.Searched.Repository.SearchedRepository;
import com.core.carryOn.Searched.domain.RecentlySearched;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchedService {
    private final SearchedRepository searchedRepository;

    public Optional<List<RecentlySearched>> findMySearched(String memberId){
        return searchedRepository.findByMemberId(memberId);
    }

    public void save(RecentlySearched recentlySearched){
        searchedRepository.save(recentlySearched);
    }

    public Optional<RecentlySearched> findByAddress(String memberId, String address) {
        return searchedRepository.findByMemberIdAndAddress(memberId, address);
    }
}
