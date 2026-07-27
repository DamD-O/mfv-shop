/**
 * @author yedam
 * @date 2026-07-27
 * @description 관리자 계정 Entity
 */
package com.example.shop.domain.admin.entity;

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
@Table(name = "admin")
public class Admin extends BaseTimeEntity
{
    @Id
    @Column(name = "admin_id", length = 50)
    private String adminId;

    @Column(name = "password", nullable = false)
    private String password;

    @Convert(converter = CryptoConverter.class)
    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", nullable = false)
    private Authority authority;

    public Admin(String adminId, String password, String phone, Authority authority)
    {
        this.adminId = adminId;
        this.password = password;
        this.phone = phone;
        this.authority = authority;
    }

    public void changePassword(String encodedPassword)
    {
        this.password = encodedPassword;
    }
}
