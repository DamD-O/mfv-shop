package com.example.shop.domain.review.controller;

import com.example.shop.domain.review.dto.ReviewImageResponse;
import com.example.shop.domain.review.entity.ReviewImage;
import com.example.shop.domain.review.service.ReviewImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author madey
 * @DATE 2026-09-08
 * @description
 */

@Tag(name = "리뷰 이미지", description = "리뷰 이미지 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/reviews")
public class ReviewImageController {
    private final ReviewImageService reviewImageService;

    @Operation(summary = "리뷰 이미지 추가")
    @PostMapping("/{reviewId}/images")
    public ReviewImageResponse uploadReviewImage(Authentication authentication, @PathVariable Long reviewId, @RequestParam MultipartFile file) throws IOException
    {
        ReviewImage image = reviewImageService.uploadImage(authentication.getName(), reviewId, file);

        return new ReviewImageResponse(image);
    }

    @Operation(summary = "리뷰 이미지 삭제")
    @DeleteMapping("/images/{imageId}")
    public void deleteReviewImage(Authentication authentication, @PathVariable Long imageId) throws IOException
    {
        reviewImageService.deleteImage(authentication.getName(), imageId);
    }
}
