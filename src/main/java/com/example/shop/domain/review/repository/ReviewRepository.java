package com.example.shop.domain.review.repository;

import com.example.shop.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOrderDetail_Product_Id(Long productId);
}
