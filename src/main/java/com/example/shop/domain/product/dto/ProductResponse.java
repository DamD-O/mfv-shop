package com.example.shop.domain.product.dto;

import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.entity.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-24
 * @description 상품 조회 시 응답 DTO
 */

@Getter
@NoArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private Integer productPrice;
    private Integer productStock;
    private ProductCategory productCategory;
    private ProductStatus productStatus;
    private String unit;

    public ProductResponse(Product product)
    {
        this.productId = product.getId();
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.productStock = product.getStock();
        this.productCategory = product.getCategory();
        this.productStatus = product.getStatus();
        this.unit = product.getUnit();
    }
}
