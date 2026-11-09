package com.example.shop.domain.customer.dto;

import com.example.shop.global.validation.ValidationPattern;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * @author madey
 * @DATE 2026-08-14
 * @description 사용자 회원가입 요청 DTO
 */

@Getter
@NoArgsConstructor
public class CustomerSignupRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{3,29}$", message = "아이디는 영문으로 시작하는 4~30자의 영문, 숫자 조합이어야 합니다.")
    private String customerId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = ValidationPattern.PASSWORD, message = ValidationPattern.PASSWORD_MESSAGE)
    private String password;

    @NotBlank(message = "이름을 입력해주세요. 최대 100자")
    @Length(max = 100, message = "100자 이내로 입력해주세요.")
    private String name;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = ValidationPattern.PHONE, message = ValidationPattern.PHONE_MESSAGE)
    private String phone;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Length(max = 255, message = "이메일은 255자 이내로 입력해주세요.")
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
