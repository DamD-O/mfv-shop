package com.example.shop.domain.customer.dto;

import com.example.shop.global.validation.ValidationPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author madey
 * @DATE 2026-10-13
 * @description
 */

@Getter
@NoArgsConstructor
public class CustomerUpdateRequest {
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String oldPassword;

    @Pattern(regexp = ValidationPattern.PASSWORD, message = ValidationPattern.PASSWORD_MESSAGE)
    private String newPassword;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Length(max = 255, message = "이메일은 255자 이내로 입력해주세요.")
    private String email;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = ValidationPattern.PHONE, message = ValidationPattern.PHONE_MESSAGE)
    private String phone;

    public CustomerUpdateRequest(String oldPassword, String newPassword, String email, String phone)
    {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.email = email;
        this.phone = phone;
    }
}
