package com.example.shop.domain.review.service;

import com.example.shop.domain.review.entity.Review;
import com.example.shop.domain.review.entity.ReviewImage;
import com.example.shop.domain.review.repository.ReviewImageRepository;
import com.example.shop.domain.review.repository.ReviewRepository;
import com.example.shop.global.entity.ImageCategory;
import com.example.shop.global.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author madey
 * @DATE 2026-09-08
 * @description
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewImageService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public ReviewImage uploadImage(String customerId, Long reviewId, MultipartFile file) throws IOException
    {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            log.warn("리뷰 이미지 업로드 - 존재하지 않는 리뷰, reviewId : {}", reviewId);
            return new RuntimeException("리뷰 이미지 업로드 실패 - 존재하지 않는 리뷰 입니다.");
        });

        if (!review.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인이 작성한 리뷰에만 이미지를 업로드 할 수 있습니다.");

        String imagePath = fileStorageService.store(file, ImageCategory.REVIEW);
        ReviewImage reviewImage = new ReviewImage(review, imagePath);

        return reviewImageRepository.save(reviewImage);
    }

    @Transactional
    public void deleteImage(String customerId, Long imageId) throws IOException
    {
        ReviewImage reviewImage = reviewImageRepository.findById(imageId).orElseThrow(() -> {
            log.warn("리뷰 이미지 삭제 실패 - imageId: {}", imageId);
            return new RuntimeException("리뷰 이미지 삭제 실패 - 존재하지 않는 이미지입니다. 다시 확인 부탁드립니다.");
        });

        if (!reviewImage.getReview().getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인이 작성한 리뷰 이미지만 삭제할 수 있습니다.");

        String filePath = reviewImage.getImagePath();

        fileStorageService.delete(filePath);
        reviewImageRepository.delete(reviewImage);
    }
}
