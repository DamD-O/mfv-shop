package com.example.shop.domain.point.repository;

import com.example.shop.domain.point.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findByCustomer_CustomerIdOrderByCreatedAtDesc(String customerId);
}
