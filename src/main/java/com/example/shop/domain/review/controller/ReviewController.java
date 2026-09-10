package com.example.shop.domain.review.controller;

import com.example.shop.domain.review.dto.ReviewRequest;
import com.example.shop.domain.review.dto.ReviewResponse;
import com.example.shop.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-09-08
 * @description
 */

@Tag(name = "리뷰", description = "리뷰 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성")
    @PostMapping("/customers/reviews")
    public ReviewResponse writeReview(@Valid @RequestBody ReviewRequest request, Authentication authentication)
    {
        return new ReviewResponse(reviewService.writeReview(authentication.getName(), request));
    }

    @Operation(summary = "상품별 리뷰 조회")
    @GetMapping("/products/{productId}/reviews")
    public List<ReviewResponse> getReviews(@PathVariable Long productId)
    {
        return reviewService.getReviewsByProductId(productId).stream().map(ReviewResponse::new).toList();
    }

    @Operation(summary = "리뷰 수정")
    @PutMapping("/customers/reviews/{reviewId}")
    public ReviewResponse updateReview(@Valid @RequestBody ReviewRequest request, @PathVariable Long reviewId, Authentication authentication)
    {
        return new ReviewResponse(reviewService.updateReview(authentication.getName(), reviewId, request));
    }

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/customers/reviews/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, Authentication authentication)
    {
        reviewService.deleteReview(authentication.getName(), reviewId);
    }
}
