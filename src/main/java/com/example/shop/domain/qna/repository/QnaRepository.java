package com.example.shop.domain.qna.repository;

import com.example.shop.domain.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByCustomer_CustomerIdOrderByCreatedAtDesc(String customerId);

    List<Qna> findByProduct_IdOrderByCreatedAtDesc(Long productId);
}
