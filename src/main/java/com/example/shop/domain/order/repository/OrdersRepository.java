package com.example.shop.domain.order.repository;

import com.example.shop.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface OrdersRepository extends JpaRepository<Orders, Long> {}
