package com.example.shop.domain.review.controller;

import com.example.shop.domain.review.dto.ReviewImageResponse;
import com.example.shop.domain.review.entity.ReviewImage;
import com.example.shop.domain.review.service.ReviewImageService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/reviews")
public class ReviewImageController {
    private final ReviewImageService reviewImageService;

    @PostMapping("/{reviewId}/images")
    public ReviewImageResponse uploadReviewImage(Authentication authentication, @PathVariable Long reviewId, @RequestParam MultipartFile file) throws IOException
    {
        ReviewImage image = reviewImageService.uploadImage(authentication.getName(), reviewId, file);

        return new ReviewImageResponse(image);
    }

    @DeleteMapping("/images/{imageId}")
    public void deleteReviewImage(Authentication authentication, @PathVariable Long imageId) throws IOException
    {
        reviewImageService.deleteImage(authentication.getName(), imageId);
    }
}
