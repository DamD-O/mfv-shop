package com.example.shop.domain.customer.entity;

import com.example.shop.domain.admin.entity.AuthType;
import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author madey
 * @date 2026-07-27
 * @description 고객 계정 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customer")
public class Customer extends BaseTimeEntity {
    @Id
    @Column(name = "customer_id", length = 30)
    private String customerId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "point", nullable = true)
    private int point = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false, length = 20)
    private AuthType authority;

    public Customer(String customerId, String password, String name, String phone, String email, LocalDate birthDate, AuthType authority)
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

    //포인트 적립
    public void earnPoint(int point)
    {
        this.point += point;
    }

    public void usePoint(int point)
    {
        //포인트 사용
        if (this.point < point)
        {
            throw new IllegalStateException("포인트가 부족합니다.");
        }
        this.point -= point;
    }
}
