package com.example.shop.domain.address.dto;

import com.example.shop.domain.address.entity.DeliveryAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-27
 * @description 배송지 응답  DTO
 */

@Getter
@NoArgsConstructor
public class DeliveryAddressResponse {
    private Long deliveryId;
    private String customerId;
    private String addressName;
    private String receiver;
    private String contact;
    private String zipcode;
    private String roadAddress;
    private String detailAddress;
    private boolean isDefault;

    public DeliveryAddressResponse(DeliveryAddress deliveryAddress)
    {
        this.deliveryId = deliveryAddress.getDeliveryId();
        this.customerId = deliveryAddress.getCustomer().getCustomerId();
        this.addressName = deliveryAddress.getAddressName();
        this.receiver = deliveryAddress.getReceiver();
        this.contact = deliveryAddress.getContact();
        this.zipcode = deliveryAddress.getZipcode();
        this.roadAddress = deliveryAddress.getRoadAddress();
        this.detailAddress = deliveryAddress.getDetailAddress();
        this.isDefault = deliveryAddress.isDefault();
    }
}
