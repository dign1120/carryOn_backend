package com.core.carryOn.member.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

    private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
    private SecurityUtil() {}

    public static Optional<String> getCurrentUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            logger.info("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String email = null;
        if(authentication.getPrincipal() instanceof UserDetails springSecurityUser){
            email = springSecurityUser.getUsername();
        } else if(authentication.getPrincipal() instanceof String){
            email = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(email);
    }
}
