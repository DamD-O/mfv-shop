package com.example.shop.domain.order.repository;

import com.example.shop.domain.order.entity.OrderStatus;
import com.example.shop.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomer_CustomerId(String customerId);

    List<Orders> findByCustomer_CustomerIdAndOrderStatus(String customerId, OrderStatus orderStatus);
}
