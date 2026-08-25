package com.example.shop.domain.product.entity;

/**
 * @author madey
 * @DATE 2026-08-24
 * @description 상품 판매 상태
 */

public enum ProductStatus {
    ON_SALE("판매중"),
    STOPPED("판매중지");

    private final String label;

    ProductStatus(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}
