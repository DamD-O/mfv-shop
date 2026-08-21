package com.example.shop.domain.customer.dto;

import com.example.shop.domain.customer.entity.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author madey
 * @DATE 2026-08-14
 * @description 사용자 회원가입 응답 DTO
 */

@Getter
@NoArgsConstructor
public class CustomerSignupResponse {
    private String customerId;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;

    public CustomerSignupResponse(Customer customer)
    {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.phone = customer.getPhone();
        this.email = customer.getEmail();
        this.birthDate = customer.getBirthDate();
    }
}
