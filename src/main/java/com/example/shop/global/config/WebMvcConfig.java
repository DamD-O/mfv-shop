package com.example.shop.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author madey
 * @DATE 2026-09-11
 * @description 정적 파일 URL로 접근 할 수 있게 설정
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/images/**").addResourceLocations("file:./images/");
    }
}
