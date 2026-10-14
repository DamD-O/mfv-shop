package com.example.shop.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author madey
 * @DATE 2026-10-14
 * @description
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtil {
    public static String getCustomerId(Authentication authentication)
    {
        return authentication != null ? authentication.getName() : null;
    }

    public static String getRole(Authentication authentication)
    {
        return authentication != null ? authentication.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("") : null;
    }
}
