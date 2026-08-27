package com.example.shop.domain.order.dto;

import com.example.shop.domain.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-27
 * @description 주문 상태 변경 요청 DTO - 관리자용
 */

@Getter
@NoArgsConstructor
public class OrderStatusUpdateRequest {

    @NotNull(message = "주문 상태를 선택해주세요.")
    private OrderStatus orderStatus;

    public OrderStatusUpdateRequest(OrderStatus orderStatus)
    {
        this.orderStatus = orderStatus;
    }
}
