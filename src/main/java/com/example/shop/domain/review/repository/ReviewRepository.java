package com.example.shop.domain.review.repository;

import com.example.shop.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {}
