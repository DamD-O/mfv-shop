package com.example.shop.domain.order.controller;

import com.example.shop.domain.order.dto.OrderCountProjection;
import com.example.shop.domain.order.dto.OrderRequest;
import com.example.shop.domain.order.dto.OrderResponse;
import com.example.shop.domain.order.dto.OrderStatusUpdateRequest;
import com.example.shop.domain.order.entity.OrderStatus;
import com.example.shop.domain.order.service.OrderService;
import com.example.shop.global.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-26
 * @description
 */

@Tag(name = "주문", description = "주문 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성")
    @PostMapping("/customers/orders")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request, Authentication authentication)
    {
        return new OrderResponse(orderService.createOrders(authentication.getName(), request));
    }

    @Operation(summary = "주문 조회")
    @GetMapping("/customers/orders")
    public PageResponse<OrderResponse> getOrders(Authentication authentication, @RequestParam(required = false) OrderStatus status, @PageableDefault(size = 3) Pageable pageable)
    {
        return orderService.getOrderList(authentication.getName(), status, pageable);
    }

    @Operation(summary = "주문 상세조회")
    @GetMapping("/customers/orders/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId, Authentication authentication)
    {
        return new OrderResponse(orderService.getOrder(authentication.getName(), orderId));
    }

    @Operation(summary = "주문 취소")
    @PatchMapping("/customers/orders/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId, Authentication authentication)
    {
        orderService.cancelOrder(authentication.getName(), orderId);
    }

    @Operation(summary = "관리자용 주문 상태 변경")
    @PatchMapping("/admin/orders/{orderId}/status")
    public OrderResponse updateOrderStatus(@PathVariable Long orderId, @Valid @RequestBody OrderStatusUpdateRequest request)
    {
        return new OrderResponse(orderService.updateOrderStatus(orderId, request.getOrderStatus()));
    }

    @Operation(summary = "결제")
    @PostMapping("/customers/orders/{orderId}/payment")
    public OrderResponse processPayment(@PathVariable Long orderId, Authentication authentication)
    {
        return new OrderResponse(orderService.processPayment(authentication.getName(), orderId));
    }

    @Operation(summary = "관리자용 전체 주문 목록 조회")
    @GetMapping("/admin/orders")
    public PageResponse<OrderResponse> getAllOrders(@RequestParam(required = false) OrderStatus status, @PageableDefault(size = 10) Pageable pageable)
    {
        return orderService.getAllOrders(status, pageable);
    }

    @Operation(summary = "주문 상태별 건 수", description = "마이페이지 주문 상태별 건수 조회")
    @GetMapping("/customers/orders/count")
    public List<OrderCountProjection> getOrdersCount(Authentication authentication)
    {
        return orderService.getOrdersCount(authentication.getName());
    }
}
