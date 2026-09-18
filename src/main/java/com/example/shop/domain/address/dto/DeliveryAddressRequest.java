package com.example.shop.domain.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-27
 * @description 배송지 요청 DTO
 */

@Getter
@NoArgsConstructor
public class DeliveryAddressRequest {

    @NotBlank(message = "배송지명을 입력해주세요.")
    private String addressName;

    @NotBlank(message = "수령인을 입력해주세요.")
    private String receiver;

    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다. (예: 010-1234-5678)")
    private String contact;

    @NotBlank(message = "우편번호를 입력해주세요.")
    private String zipcode;

    @NotBlank(message = "주소를 선택해주세요.")
    private String roadAddress;

    private String detailAddress;

    public DeliveryAddressRequest(String addressName, String receiver, String contact, String zipcode, String roadAddress, String detailAddress)
    {
        this.addressName = addressName;
        this.receiver = receiver;
        this.contact = contact;
        this.zipcode = zipcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }
}
