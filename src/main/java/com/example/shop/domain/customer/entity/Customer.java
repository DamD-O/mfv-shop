/**
 * @author yedam
 * @date 2026-07-27
 * @description 고객 계정 Entity
 */
package com.example.shop.domain.customer.entity;

import com.example.shop.domain.admin.entity.Authority;
import com.example.shop.global.converter.CryptoConverter;
import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "customer")
public class Customer extends BaseTimeEntity
{
    @Id
    @Column(name = "customer_id", length = 30)
    private String customerId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Convert(converter = CryptoConverter.class)
    @Column(name = "phone", nullable = false)
    private String phone;

    @Convert(converter = CryptoConverter.class)
    @Column(name = "email", nullable = false)
    private String email;

    @Convert(converter = CryptoConverter.class)
    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @Column(name = "point", nullable = true)
    private int point = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", nullable = false)
    private Authority authority;

    public Customer(String customerId, String password, String name, String phone, String email, String birthDate, Authority authority)
    {
        this.customerId = customerId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.authority = authority;
    }

    public void changePassword(String encodedPassword)
    {
        this.password = encodedPassword;
    }

    public void updateContact(String phone, String email)
    {
        this.phone = phone;
        this.email = email;
    }

    public void earnPoint(int amount)
    {
        this.point += amount;
    } //포인트 적립

    public void usePoint(int amount)
    {
        //포인트 사용
        if (this.point < amount)
        {
            throw new IllegalStateException("포인트가 부족합니다.");
        }
        this.point -= amount;
    }
}
