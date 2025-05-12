package com.core.carryOn.member.controller;

import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.dto.LoginDto;
import com.core.carryOn.member.dto.RegisterFormDto;
import com.core.carryOn.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Member> join(@Valid  @RequestBody RegisterFormDto member) {
        Member newMember = new Member();
        newMember.setEmail(member.getEmail());
        newMember.setNickname(member.getNickname());
        newMember.setPassword(member.getPassword());
        newMember.setProvider(member.getProvider());
        newMember.setProviderId(member.getProviderId());
        newMember.setCreatedAt(LocalDateTime.now());
        newMember.setLastLoginAt(LocalDateTime.now());
        memberService.register(newMember);

        return ResponseEntity.ok(newMember);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Member> getMyUserInfo() {
        return ResponseEntity.ok(memberService.getMyMemberWithAuthority().get());
    }

    @PostMapping("/exist-email")
    public ResponseEntity<Map<String, Object>> existEmail(@RequestParam("email") String email) {
        boolean exists = memberService.findOneByEmail(email).isPresent();
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/exist-nickname")
    public ResponseEntity<Map<String, Object>> existNickname(@RequestParam("nickname") String nickname) {
        boolean exists = memberService.findOneByNickname(nickname).isPresent();
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

}
