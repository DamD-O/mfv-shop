/**
 * @author yedam
 * @date 2026-07-27
 * @description 고객 배송지 Entity
 */
package com.example.shop.domain.address.entity;

import com.example.shop.domain.customer.entity.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "delivery_address")
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "address_name", nullable = false, length = 100)
    private String addressName;

    @Column(name = "receiver", nullable = false, length = 50)
    private String receiver;

    @Column(name = "contact", nullable = false, length = 20)
    private String contact;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    public DeliveryAddress(Customer customer, Address address, String addressName, String receiver, String contact, boolean isDefault)
    {
        this.customer = customer;
        this.address = address;
        this.addressName = addressName;
        this.receiver = receiver;
        this.contact = contact;
        this.isDefault = isDefault;
    }

    @Override
    public String toString()
    {
        return "DeliveryAddress{" + "id=" + id + ", customer=" + customer + ", address=" + address + ", addressName='" + addressName + '\'' + ", receiver='" + receiver + '\'' + ", contact='" + contact + '\'' + ", isDefault=" + isDefault + '}';
    }

    public void changeDefault(boolean isDefault)
    {
        this.isDefault = isDefault;
    }
}
