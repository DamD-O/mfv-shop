package com.example.shop.domain.point.repository;

import com.example.shop.domain.point.entity.PointHistory;
import com.example.shop.domain.point.entity.PointType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    Page<PointHistory> findByCustomer_CustomerId(String customerId, Pageable pageable);

    boolean existsByOrders_IdAndType(Long orderId, PointType pointType);
}

