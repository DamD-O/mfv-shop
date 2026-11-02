package com.example.shop.domain.qna.repository;

import com.example.shop.domain.qna.entity.Qna;
import com.example.shop.domain.qna.entity.QnaStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface QnaRepository extends JpaRepository<Qna, Long> {
    Page<Qna> findByCustomer_CustomerId(String customerId, Pageable pageable);

    Page<Qna> findByProduct_Id(Long productId, Pageable pageable);

    Page<Qna> findByStatus(QnaStatus status, Pageable pageable);
}
