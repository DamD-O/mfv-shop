package com.example.shop.domain.order.entity;

/**
 * @author madey
 * @date 2026-07-27
 * @description 주문 상태 Enum
 */

public enum OrderStatus {
    PAYMENT_PENDING("결제 대기"),
    PAYMENT_COMPLETE("결제 완료"),
    SHIPPING("배송 중"),
    DELIVERED("배송 완료"),
    CANCELED("취소");

    private final String label;

    OrderStatus(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}

