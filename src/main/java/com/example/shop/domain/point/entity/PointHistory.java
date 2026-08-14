package com.example.shop.domain.point.entity;

import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.order.entity.Orders;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * @description 포인트 적립/사용 이력 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_history")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private PointType type;

    @Column(name = "point", nullable = false)
    private int point;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public PointHistory(Customer customer, Orders orders, PointType type, int point)
    {
        this.customer = customer;
        this.orders = orders;
        this.type = type;
        this.point = point;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString()
    {
        return "PointHistory{" + "id=" + id + ", customer=" + customer + ", orders=" + orders + ", type=" + type + ", point=" + point + ", createdAt=" + createdAt + '}';
    }
}
