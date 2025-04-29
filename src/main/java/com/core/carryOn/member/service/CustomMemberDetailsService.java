package com.core.carryOn.member.service;

import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // 커스텀에서는 이메일로 찾기
        return memberRepository.findOneWithAuthorityByEmail(username)
                .map(member -> createUser(username, member))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String email, Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority());
        return new org.springframework.security.core.userdetails.User(email, member.getPassword(), List.of(grantedAuthority));
    }
}
