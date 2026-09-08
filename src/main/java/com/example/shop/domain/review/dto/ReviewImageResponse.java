package com.example.shop.domain.review.dto;

import com.example.shop.domain.review.entity.ReviewImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-09-08
 * @description 리뷰 이미지 응답 DTO
 */

@Getter
@NoArgsConstructor
public class ReviewImageResponse {
    private Long imageId;
    private String imagePath;

    public ReviewImageResponse(ReviewImage reviewImage)
    {
        this.imageId = reviewImage.getId();
        this.imagePath = reviewImage.getImagePath();
    }
}
