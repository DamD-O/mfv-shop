package com.example.shop.domain.order.repository;

import com.example.shop.domain.order.dto.SalesProductRankProjection;
import com.example.shop.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description 상세 주문 관련
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query(value = """
            SELECT p.product_id AS productId, p.name AS productName, SUM(od.quantity) AS totalQuantity
            FROM order_detail od
            JOIN orders o ON od.order_id = o.order_id
            JOIN product p ON od.product_id = p.product_id
            WHERE o.order_status='DELIVERED' AND o.created_at >= :startDate
            GROUP BY p.product_id, p.name
            ORDER BY totalQuantity desc, productName
            LIMIT 10
            """, nativeQuery = true)
    List<SalesProductRankProjection> findProductSalesRank(@Param("startDate") LocalDateTime startDate);
}
