package com.example.shop.domain.admin.entity;

import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @date 2026-07-27
 * @description 관리자 계정 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin")
public class Admin extends BaseTimeEntity {
    @Id
    @Column(name = "admin_id", length = 50)
    private String adminId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false, length = 20)
    private AuthType authority;

    public Admin(String adminId, String password, String phone, AuthType authority)
    {
        this.adminId = adminId;
        this.password = password;
        this.phone = phone;
        this.authority = authority;
    }

    @Override
    public String toString()
    {
        return "Admin{" + "adminId='" + adminId + '\'' + ", password='" + password + '\'' + ", phone='" + phone + '\'' + ", authority=" + authority + '}';
    }

    public void changePassword(String encodedPassword)
    {
        this.password = encodedPassword;
    }
}
