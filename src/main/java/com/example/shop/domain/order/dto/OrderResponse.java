package com.example.shop.domain.order.dto;

import com.example.shop.domain.order.entity.OrderStatus;
import com.example.shop.domain.order.entity.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-26
 * @description 주문 응답 DTO
 */

@Getter
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String customerId;
    private Long deliveryId;
    private String addressSnapshot;
    private int totalAmount;
    private OrderStatus orderStatus;
    private List<OrderDetailResponse> orderDetails;

    public OrderResponse(Orders orders)
    {
        this.orderId = orders.getId();
        this.customerId = orders.getCustomer().getCustomerId();
        this.deliveryId = orders.getDeliveryAddress().getId();
        this.addressSnapshot = orders.getAddressSnapshot();
        this.totalAmount = orders.getTotalAmount();
        this.orderStatus = orders.getOrderStatus();
        this.orderDetails = orders.getOrderDetails().stream().map(OrderDetailResponse::new).toList();
    }
}
