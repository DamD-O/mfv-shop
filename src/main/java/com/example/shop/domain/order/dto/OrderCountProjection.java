package com.example.shop.domain.order.dto;

/**
 * @author madey
 * @DATE 2026-10-29
 * @description 주문 상태 별 개수 조회
 */

public interface OrderCountProjection {
    String getOrderStatus();

    Long getOrderStatusCount();
}
