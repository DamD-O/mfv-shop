package com.example.shop.domain.customer.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author madey
 * @DATE 2026-08-14
 * @description 사용자 회원가입 요청 DTO
 */

@Getter
@NoArgsConstructor
public class CustomerSignupRequest {

    @NotBlank(message =  "아이디를 입력해주세요.")
    private String customerId;

    @NotBlank(message =  "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다. (예: 010-1234-5678)")
    private String phone;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotNull(message = "생년월일을 입력해주세요.")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birthDate;

    public CustomerSignupRequest(String customerId, String password, String name, String phone, String email, LocalDate birthDate)
    {
        this.customerId = customerId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
    }
}
