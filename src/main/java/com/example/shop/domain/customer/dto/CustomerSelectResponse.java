package com.example.shop.domain.customer.dto;

import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.entity.CustomerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author madey
 * @DATE 2026-10-28
 * @description 관리자 회원 정보 응답 DTO
 */

@Getter
@NoArgsConstructor
public class CustomerSelectResponse {
    private String customerId;
    private String customerName;
    private String email;
    private String phone;
    private int point;
    private boolean withdrawn;
    private String withdrawnLabel;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime withdrawnAt;

    public CustomerSelectResponse(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerName = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.point = customer.getPoint();
        this.withdrawn = customer.isWithdrawn();
        this.withdrawnLabel = CustomerStatus.getStatusLabel(customer.isWithdrawn());
        this.birthDate = customer.getBirthDate();
        this.createdAt = customer.getCreatedAt();
        this.withdrawnAt = withdrawn ? customer.getWithdrawnAt() : null;
    }
}