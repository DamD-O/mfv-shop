package com.example.shop.domain.order.controller;

import com.example.shop.domain.order.dto.OrderRequest;
import com.example.shop.domain.order.dto.OrderResponse;
import com.example.shop.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author madey
 * @DATE 2026-08-26
 * @description
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class OrderController {
    private final OrderService orderService;

    //주문 생성
    @PostMapping("/orders")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request, Authentication authentication)
    {
        return new OrderResponse(orderService.createOrders(authentication.getName(), request));
    }
}
