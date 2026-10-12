package com.example.shop.global.security.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author madey
 * @DATE 2026-08-18
 * @description 로그인 실패 시 원인별 에러 메시지를 담아 401 응답 반환
 */

public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
    {
        String errMsg, errCode;

        if (exception instanceof DisabledException)
        {
            errMsg = "탈퇴한 사용자입니다.";
            errCode = "WITHDRAWN";
        }
        else if (exception instanceof BadCredentialsException)
        {
            errMsg = "아이디 또는 비밀번호가 올바르지 않습니다.";
            errCode = "BAD_CREDENTIALS";
        }
        else
        {
            errMsg = "로그인에 실패했습니다. 잠시후 다시 시도해주세요.";
            errCode = "LOGIN_FAILED";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("error", errCode);
        map.put("message", errMsg);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}
