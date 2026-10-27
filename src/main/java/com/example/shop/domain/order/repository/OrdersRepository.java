package com.example.shop.domain.order.repository;

import com.example.shop.domain.order.dto.OrderStatsProjection;
import com.example.shop.domain.order.entity.OrderStatus;
import com.example.shop.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomer_CustomerId(String customerId);

    List<Orders> findByCustomer_CustomerIdAndOrderStatus(String customerId, OrderStatus orderStatus);

    boolean existsByCustomer_CustomerIdAndOrderStatusIn(String customerId, List<OrderStatus> orderStatuses);

    List<Orders> findByOrderStatusOrderByCreatedAtDesc(OrderStatus status);

    @Query(value = """ 
            SELECT DATE_FORMAT(created_at, :pattern) AS baseDate, count(created_at) AS orderCount
             FROM orders
             WHERE order_status='DELIVERED'
             AND created_at >= :startDate
             GROUP BY baseDate
             ORDER BY baseDate
            """, nativeQuery = true)
    List<OrderStatsProjection> findDateCompletedDeliveryCounts(@Param("pattern") String pattern, @Param("startDate") LocalDateTime startDate);
}

