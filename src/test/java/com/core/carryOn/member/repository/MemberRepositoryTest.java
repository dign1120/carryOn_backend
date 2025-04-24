package com.core.carryOn.member.repository;

import com.core.carryOn.MongoDBConfig;
import com.core.carryOn.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Import(MongoDBConfig.class)
class MemberRepositoryTest {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
    public void findByEmail() {
        Member member = memberRepository.findByEmail("test@example.com")
                .orElseThrow(() -> new NoSuchElementException("member not found"));
        assertEquals("testUser", member.getNickname());
    }

    @Test
    public void save() {
        Member newMember = new Member();
        newMember.setEmail("test2@example");
        newMember.setNickname("newNickname");
        memberRepository.save(newMember);

        Member testMember = memberRepository.findByEmail(newMember.getEmail())
                .orElseThrow(() -> new NoSuchElementException("test2 not found"));
        assertEquals("newNickname", testMember.getNickname());
    }
}