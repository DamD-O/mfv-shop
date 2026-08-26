package com.example.shop.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-26
 * @description 장바구니 항목 DTO
 */

@Getter
@NoArgsConstructor
public class OrderItemRequest {

    @NotNull(message = "상품을 선택해주세요.")
    private Long productId;

    @NotNull(message = "수량을 입력해주세요.")
    @Positive(message = "수량은 1개 이상이어야 합니다.")
    private Integer quantity;

    public OrderItemRequest(Long productId, Integer quantity)
    {
        this.productId = productId;
        this.quantity = quantity;
    }
}
