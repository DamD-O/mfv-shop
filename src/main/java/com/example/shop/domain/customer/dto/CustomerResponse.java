package com.example.shop.domain.customer.dto;

import com.example.shop.domain.customer.entity.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author madey
 * @DATE 2026-10-13
 * @description 사용자 응답 DTO
 */

@Getter
@NoArgsConstructor
public class CustomerResponse {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;

    public CustomerResponse(Customer customer)
    {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.birthDate = customer.getBirthDate();
    }

}
