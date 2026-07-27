/**
 * @author yedam
 * @date 2026-07-27
 * @description 관리자/고객 권한 Entity
 */
package com.example.shop.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "authority")
public class Authority
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    @Column(name = "authority_name", nullable = false, length = 30)
    private String authorityName;

    public Authority(String authorityName)
    {
        this.authorityName = authorityName;
    }
}
