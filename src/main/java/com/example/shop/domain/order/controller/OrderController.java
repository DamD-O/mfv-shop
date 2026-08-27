package com.example.shop.domain.order.controller;

import com.example.shop.domain.order.dto.OrderRequest;
import com.example.shop.domain.order.dto.OrderResponse;
import com.example.shop.domain.order.dto.OrderStatusUpdateRequest;
import com.example.shop.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-26
 * @description
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    //주문 생성
    @PostMapping("/customers/orders")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request, Authentication authentication)
    {
        return new OrderResponse(orderService.createOrders(authentication.getName(), request));
    }

    //주문 조회
    @GetMapping("/customers/orders")
    public List<OrderResponse> getOrders(Authentication authentication)
    {
        return orderService.getOrderList(authentication.getName()).stream().map(OrderResponse::new).toList();
    }

    //주문 상세조회
    @GetMapping("/customers/orders/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId, Authentication authentication)
    {
        return new OrderResponse(orderService.getOrder(authentication.getName(), orderId));
    }

    //주문 취소
    @PatchMapping("/customers/orders/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId, Authentication authentication)
    {
        orderService.cancelOrder(authentication.getName(), orderId);
    }

    //주문 상태변경 - 관리자
    @PatchMapping("/admin/orders/{orderId}/status")
    public OrderResponse updateOrderStatus(@PathVariable Long orderId, @Valid @RequestBody OrderStatusUpdateRequest request)
    {
        return new OrderResponse(orderService.updateOrderStatus(orderId, request.getOrderStatus()));
    }
}
