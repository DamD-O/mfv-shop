package com.example.shop.domain.order.repository;

import com.example.shop.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yedam
 * @DATE 2026-07-28
 * @description
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>
{}
