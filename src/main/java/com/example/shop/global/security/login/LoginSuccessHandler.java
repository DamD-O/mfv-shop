package com.example.shop.global.security.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author madey
 * @DATE 2026-08-18
 * @description 로그인 성공 핸들러
 */

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        //로그인한 사용자 권한
        boolean isAdmin = authentication.getAuthorities()
                                        .stream()
                                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        String role = isAdmin ? "ADMIN" : "USER";

        Map<String, Object> map = new HashMap<>();
        map.put("message", "로그인 성공");
        map.put("userID", authentication.getName());
        map.put("role", role);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}
