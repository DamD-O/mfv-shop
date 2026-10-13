package com.example.shop.domain.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])\\S{8,}$", message = "비밀번호는 영문, 숫자, 특수문자(!,@,#,$,%,^,&,*) 포함 8자 이상이며 공백은 사용할 수 없습니다.")
    private String newPassword;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다. (예: 010-1234-5678)")
    private String phone;

    public CustomerUpdateRequest(String oldPassword, String newPassword, String email, String phone)
    {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.email = email;
        this.phone = phone;
    }
}
