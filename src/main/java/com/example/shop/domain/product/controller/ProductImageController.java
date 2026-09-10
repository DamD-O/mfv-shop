package com.example.shop.domain.product.controller;

import com.example.shop.domain.product.dto.ProductImageResponse;
import com.example.shop.domain.product.entity.ProductImage;
import com.example.shop.domain.product.service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author madey
 * @DATE 2026-08-25
 * @description
 */

@Tag(name = "상품 이미지", description = "상품 이미지 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/products")
public class ProductImageController {
    private final ProductImageService productImageService;

    @Operation(summary = "상품 이미지 업로드")
    @PostMapping("/{productId}/images")
    public ProductImageResponse uploadImage(@PathVariable Long productId, @RequestParam MultipartFile file) throws IOException
    {
        ProductImage image = productImageService.uploadImage(productId, file);

        return new ProductImageResponse(image);
    }

    @Operation(summary = "상품 이미지 삭제")
    @DeleteMapping("/images/{imageId}")
    public void deleteImage(@PathVariable Long imageId) throws IOException
    {
        productImageService.deleteImage(imageId);
    }
}
