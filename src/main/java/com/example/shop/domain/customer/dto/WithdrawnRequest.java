package com.example.shop.domain.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-09-09
 * @description
 */

@Getter
@NoArgsConstructor
public class WithdrawnRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public WithdrawnRequest(String password)
    {
        this.password = password;
    }
}
