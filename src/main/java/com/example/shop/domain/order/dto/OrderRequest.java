package com.example.shop.domain.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-26
 * @description 고객 주문 요청 DTO
 */

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotNull(message = "배송 주소를 선택해주세요.")
    private Long deliveryId;

    @NotEmpty(message = "주문할 상품을 선택해주세요.")
    @Valid //리스트 안의 각 객체도 검증
    private List<OrderItemRequest> orderItemRequest;

    public OrderRequest(Long deliveryId, List<OrderItemRequest> orderItemRequest)
    {
        this.deliveryId = deliveryId;
        this.orderItemRequest = orderItemRequest;
    }
}
