package com.example.shop.domain.review.dto;

import com.example.shop.domain.product.entity.ProductImage;
import com.example.shop.domain.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private Long productId;
    private String productName;
    private String productImageUrl;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReviewImageResponse> reviewImages;

    public ReviewResponse(Review review)
    {
        this.reviewId = review.getId();
        this.customerId = review.getCustomer().getCustomerId();
        this.orderDetailId = review.getOrderDetail().getId();
        this.productId = review.getOrderDetail().getProduct().getId();
        this.productName = review.getOrderDetail().getProduct().getName();
        this.productImageUrl = review.getOrderDetail()
                                     .getProduct()
                                     .getProductImages()
                                     .stream()
                                     .findFirst()
                                     .map(ProductImage::getImagePath)
                                     .orElse(null);
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
        this.reviewImages = review.getReviewImages().stream().map(ReviewImageResponse::new).toList();
    }
}
