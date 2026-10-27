package com.example.shop.domain.admin.dto;

import com.example.shop.domain.order.dto.SalesProductRankProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-10-27
 * @description 상품 판매량 순위 응답 DTO
 */

@Getter
@NoArgsConstructor
public class SalesProductRankResponse {
    private Long productId;
    private String productName;
    private Long totalQuantity;

    public SalesProductRankResponse(SalesProductRankProjection projection)
    {
        this.productId = projection.getProductId();
        this.productName = projection.getProductName();
        this.totalQuantity = projection.getTotalQuantity();
    }
}
