package com.example.shop.domain.address.entity;

import com.example.shop.domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author madey
 * @date 2026-07-27
 * @description 고객 배송지 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "delivery_address")
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "address_name", nullable = false, length = 100)
    private String addressName;

    @Column(name = "receiver", nullable = false, length = 50)
    private String receiver;

    @Column(name = "contact", nullable = false, length = 20)
    private String contact;

    @Column(name = "zipcode", nullable = false, length = 10)
    private String zipcode;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @Setter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public DeliveryAddress(Customer customer, String addressName, String receiver, String contact, String zipcode, String roadAddress, String detailAddress, boolean isDefault, LocalDateTime deletedAt)
    {
        this.customer = customer;
        this.addressName = addressName;
        this.receiver = receiver;
        this.contact = contact;
        this.zipcode = zipcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
        this.deletedAt = deletedAt;
    }

    //주소 수정
    public void update(String addressName, String receiver, String contact, String zipcode, String roadAddress, String detailAddress)
    {
        this.addressName = addressName;
        this.receiver = receiver;
        this.contact = contact;
        this.zipcode = zipcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }

    public void changeDefault(boolean isDefault)
    {
        this.isDefault = isDefault;
    }
}
