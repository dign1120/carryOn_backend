package com.core.carryOn.member.service;

import com.core.carryOn.member.Utils.SecurityUtil;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Member member) {
        if(memberRepository.findOneWithAuthorityByEmail(member.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 회원입니다,");
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setAuthority("ROLE_USER");
        memberRepository.save(member);
    }

    public Optional<Member> findOneByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> getMyMemberWithAuthority() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(memberRepository:: findOneWithAuthorityByEmail);
    }
}
