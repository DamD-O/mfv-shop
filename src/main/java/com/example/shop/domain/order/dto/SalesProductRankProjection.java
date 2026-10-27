package com.example.shop.domain.order.dto;

/**
 * @author madey
 * @DATE 2026-10-27
 * @description 상품 판매량 순위
 */

public interface SalesProductRankProjection {
    Long getProductId();
    String getProductName();
    Long getTotalQuantity();
}
