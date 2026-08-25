package com.example.shop.domain.product.service;

import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.entity.ProductImage;
import com.example.shop.domain.product.repository.ProductImageRepository;
import com.example.shop.domain.product.repository.ProductRepository;
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
 * @DATE 2026-08-25
 * @description 상품 이미지 서비스
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public ProductImage uploadImage(Long productId, MultipartFile file) throws IOException
    {
        // 1. productId로 Product 조회 (없으면 예외)
        // 2. fileStorageService.store(file, ImageCategory.PRODUCT) 호출해서 경로 받기
        // 3. ProductImage 생성해서 저장
        // 4. 리턴
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            log.warn("이미지 업로드 실패 - 존재하지 않는 productId: {}", productId);
            return new RuntimeException("해당 상품을 찾을 수 없습니다. 목록을 새로고침 후 다시 시도해주세요.");
        });

        String imagePath = fileStorageService.store(file, ImageCategory.PRODUCT);
        ProductImage productImage = new ProductImage(product, imagePath);

        return productImageRepository.save(productImage);
    }

    @Transactional
    public void deleteImage(Long imageId) throws IOException
    {
        // 1. imageId로 ProductImage 조회 (없으면 예외)
        // 2. 그 안의 imagePath로 fileStorageService.delete() 호출
        // 3. productImageRepository.delete()로 DB row 삭제
        ProductImage productImage = productImageRepository.findById(imageId).orElseThrow(() -> {
            log.warn("이미지 삭제 실패 - imageId: {}", imageId);
            return new RuntimeException("이미지 삭제 실패 - 존재하지 않는 이미지입니다. 다시 확인 부탁드립니다.");
        });

        String filePath = productImage.getImagePath();

        fileStorageService.delete(filePath);
        productImageRepository.delete(productImage);
    }
}
