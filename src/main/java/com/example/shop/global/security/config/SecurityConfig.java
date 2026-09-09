package com.example.shop.global.security.config;

import com.example.shop.global.security.login.CustomAuthenticationEntryPoint;
import com.example.shop.global.security.login.LoginFailureHandler;
import com.example.shop.global.security.login.LoginSuccessHandler;
import com.example.shop.global.security.login.LogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description 보안 관련 Bean
 * 패스워드 암호화 등
 */

@Configuration
public class SecurityConfig {

    //스프링 시큐리티 기능 비활성화 : 시큐리티 모든 기능을 사용하지 않게 설정
    @Bean
    public WebSecurityCustomizer configure()
    {
        return (web) -> web.ignoring().requestMatchers("/", "/*.html", "/css/**", "/js/**", "/images/**", "/assets/**");
    }

    //패스워드 인코더
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        return http.authorizeHttpRequests(
                           auth -> auth.requestMatchers("/api/customers/signup", "/api/login", "/api/logout", "/error", "/api/products",
                                                        "/api/products/**", "/api/qna/**", "/api/ai/**")
                                       .permitAll()
                                       .requestMatchers("/api/admin/**")
                                       .hasRole("ADMIN") // ROLE_ADMIN 권한 필요
                                       .requestMatchers("/api/customers/**")
                                       .hasAnyRole("ADMIN", "USER") // USER 또는 ADMIN 권한 필요
                                       .anyRequest()
                                       .authenticated())
                   .formLogin(formLogin -> formLogin.loginProcessingUrl("/api/login")
                                                    .successHandler(new LoginSuccessHandler())
                                                    .failureHandler(new LoginFailureHandler()))
                   .exceptionHandling(e -> e.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                   .logout(logout -> logout.logoutUrl("/api/logout")
                                           .logoutSuccessHandler(new LogoutSuccessHandler())
                                           .invalidateHttpSession(true)
                                           .deleteCookies("JSESSIONID"))
                   .csrf(csrf -> csrf.disable())
                   .build();
    }
}
