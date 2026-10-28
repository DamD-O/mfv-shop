package com.example.shop.domain.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author madey
 * @DATE 2026-10-28
 * @description 회원 이름 변경 요청 DTO
 */

@Getter
@NoArgsConstructor
public class UpdateCustomerNameRequest {

    @NotBlank(message = "변경할 이름을 입력해주세요. 최대 100자")
    @Length(max = 100, message = "100자 이내로 입력해주세요.")
    private String customerName;

    public UpdateCustomerNameRequest(String customerName)
    {
        this.customerName = customerName;
    }
}