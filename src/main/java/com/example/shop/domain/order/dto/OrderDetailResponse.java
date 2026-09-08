package com.example.shop.domain.order.dto;

import com.example.shop.domain.order.entity.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-26
 * @description 상세 주문 응답 DTO
 */

@Getter
@NoArgsConstructor
public class OrderDetailResponse {
    private Long orderDetailId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer priceAtOrder;

    public OrderDetailResponse(OrderDetail orderDetail)
    {
        this.orderDetailId = orderDetail.getId();
        this.productId = orderDetail.getProduct().getId();
        this.productName = orderDetail.getProduct().getName();
        this.quantity = orderDetail.getQuantity();
        this.priceAtOrder = orderDetail.getPriceAtOrder();
    }
}
