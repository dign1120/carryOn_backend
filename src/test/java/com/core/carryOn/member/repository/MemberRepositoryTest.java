package com.core.carryOn.member.repository;

import com.core.carryOn.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class MemberRepositoryTest {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
    public void findById() {
        Member member = memberRepository.findByName("test1")
                .orElseThrow(() -> new NoSuchElementException("test1 not found"));
        assertEquals("test1", member.getName());
    }

    @Test
    public void save() {
        Member newMember = new Member();
        newMember.setName("test2");
        memberRepository.save(newMember);

        Member testMember = memberRepository.findByName(newMember.getName())
                .orElseThrow(() -> new NoSuchElementException("test2 not found"));
        assertEquals("test2", testMember.getName());
    }
}