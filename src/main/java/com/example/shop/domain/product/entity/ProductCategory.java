package com.example.shop.domain.product.entity;

/**
 * @author madey
 * @date 2026-07-27
 * @description 상품 카테고리 Enum (MEAT, FRUIT, VEGETABLE)
 */

public enum ProductCategory {
    MEAT("육류"),
    FRUIT("과일"),
    VEGETABLE("채소"),
    OTHER("기타");

    private final String label;

    private ProductCategory(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}
