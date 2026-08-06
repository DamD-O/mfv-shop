package com.example.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //Entity가 저장/수정될 때마다 자동으로 현재 시각을 채워줌
@SpringBootApplication
public class ShopApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(ShopApplication.class, args);
    }

}
