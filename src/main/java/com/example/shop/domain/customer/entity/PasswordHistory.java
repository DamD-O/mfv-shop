package com.example.shop.domain.customer.entity;

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

import java.time.LocalDateTime;

/**
 * @author madey
 * @date 2026-07-27
 * @description 고객 비밀번호 변경 이력 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "password_history")
public class PasswordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "old_password", nullable = false)
    private String oldPassword;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public PasswordHistory(Customer customer, String oldPassword)
    {
        this.customer = customer;
        this.oldPassword = oldPassword;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString()
    {
        return "PasswordHistory{" + "id=" + id + ", customer=" + customer + ", oldPassword='" + oldPassword + '\'' + ", createdAt=" + createdAt + '}';
    }
}
