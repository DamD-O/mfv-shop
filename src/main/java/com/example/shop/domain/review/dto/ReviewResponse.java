package com.example.shop.domain.review.dto;

import com.example.shop.domain.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author madey
 * @DATE 2026-09-08
 * @description
 */

@Getter
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private String customerId;
    private Long orderDetailId;
    private String productName;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewResponse(Review review)
    {
        this.reviewId = review.getId();
        this.customerId = review.getCustomer().getCustomerId();
        this.orderDetailId = review.getOrderDetail().getId();
        this.productName = review.getOrderDetail().getProduct().getName();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
}
