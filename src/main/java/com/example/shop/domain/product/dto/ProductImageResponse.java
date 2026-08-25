package com.example.shop.domain.product.dto;

import com.example.shop.domain.product.entity.ProductImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-25
 * @description 상품 이미지 응답 DTO
 */

@Getter
@NoArgsConstructor
public class ProductImageResponse {
    private Long imageId;
    private String imagePath;

    public ProductImageResponse(ProductImage productImage)
    {
        this.imageId = productImage.getId();
        this.imagePath = productImage.getImagePath();
    }
}
