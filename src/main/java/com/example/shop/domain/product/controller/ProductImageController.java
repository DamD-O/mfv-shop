package com.example.shop.domain.product.controller;

import com.example.shop.domain.product.dto.ProductImageResponse;
import com.example.shop.domain.product.entity.ProductImage;
import com.example.shop.domain.product.service.ProductImageService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/products")
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping("/{productId}/images")
    public ProductImageResponse uploadImage(@PathVariable Long productId, @RequestParam MultipartFile file) throws IOException
    {
        ProductImage image = productImageService.uploadImage(productId, file);

        return new ProductImageResponse(image);
    }

    @DeleteMapping("/images/{imageId}")
    public void deleteImage(@PathVariable Long imageId) throws IOException
    {
        productImageService.deleteImage(imageId);
    }
}
