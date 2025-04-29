package com.core.carryOn.Location.Repository;

import com.core.carryOn.Location.domain.Location;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class LocationRepositoryTest {
    private final LocationRepository locationRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LocationRepositoryTest(LocationRepository locationRepository, MemberRepository memberRepository) {
        this.locationRepository = locationRepository;
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("멤버를 통해서 데이터 찾기")
    public void findByMemberOne() {
        Member member = memberRepository.findByEmail("test@example.com")
                .orElseThrow(() -> new NoSuchElementException("멤버가 없습니다."));

        Optional<Location> findMember = locationRepository.findByMemberId(member.getId());
        assertTrue(findMember.isPresent());

    }
}