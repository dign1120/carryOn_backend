package com.core.carryOn.Searched.Controller;

import com.core.carryOn.Searched.Service.SearchedService;
import com.core.carryOn.Searched.domain.RecentlySearched;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchedController {
    private final MemberService memberService;
    private final SearchedService searchedService;

    @GetMapping("/my-searched")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<RecentlySearched>> mySearched(){
        Member member = memberService.getMyMemberWithAuthority()
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));

        List<RecentlySearched> recentlySearcheds = searchedService.findMySearched(member.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 회원의 검색목록이 없습니다."));

        return ResponseEntity.ok(recentlySearcheds);
    }

    @PostMapping("/add-my-searching")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RecentlySearched> addMySearched(@RequestBody RecentlySearched recentlySearched){
        Member member = memberService.getMyMemberWithAuthority()
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));

        Optional<RecentlySearched> byAddress = searchedService.findByAddress(member.getId(), recentlySearched.getAddress());
        if(byAddress.isPresent()){
            RecentlySearched existedSearched = byAddress.get();
            existedSearched.setCreatedAt(LocalDateTime.now());
            searchedService.save(existedSearched);
            return ResponseEntity.ok(existedSearched);
        } else {
            recentlySearched.setMemberId(member.getId());
            recentlySearched.setCreatedAt(LocalDateTime.now());
            searchedService.save(recentlySearched);
            return ResponseEntity.ok(recentlySearched);
        }
    }
}
