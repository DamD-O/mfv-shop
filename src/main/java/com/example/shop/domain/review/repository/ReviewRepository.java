package com.example.shop.domain.review.repository;

import com.example.shop.domain.order.entity.OrderDetail;
import com.example.shop.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByOrderDetail_Product_Id(Long productId, Pageable pageable);

    Page<Review> findByCustomer_CustomerId(String customerCustomerId, Pageable pageable);

    List<Review> findByOrderDetailIn(List<OrderDetail> details);

    boolean existsByOrderDetail_Id(Long orderDetailId);
}
