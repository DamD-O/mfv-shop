package com.example.shop.domain.order.entity;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * @author madey
 * @date 2026-07-27
 * @description 주문 상태 Enum
 */

public enum OrderStatus {
    PAYMENT_PENDING("결제 대기"),
    PAYMENT_COMPLETE("결제 완료"),
    PREPARING("배송준비중"),
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

    public static List<OrderStatus> notAvailWithdrawnStatus()
    {
        EnumSet<OrderStatus> excludeStatus = EnumSet.of(OrderStatus.CANCELED, OrderStatus.DELIVERED);
        return Arrays.stream(OrderStatus.values()).filter(status -> !excludeStatus.contains(status)).toList();
    }

    public boolean canChangeTo(OrderStatus next)
    {
        /*
         * 관리자가 주문 상태 허용 전이 규칙
         * 결제 완료 -> 배송 준비 중
         * 배송 준비중 -> 배송중 / 취소
         * 배송중 -> 배송 완료
         * 뒤로 가기 안됨
         * */
        return switch (this)
        {
            case PAYMENT_COMPLETE -> next == OrderStatus.PREPARING;
            case PREPARING -> next == OrderStatus.SHIPPING || next == OrderStatus.CANCELED;
            case SHIPPING -> next == OrderStatus.DELIVERED;
            default -> false;
        };
    }

    public void validateChangeTo(OrderStatus next)
    {
        if (!canChangeTo(next))
        {
            throw new IllegalStateException(String.format("[%s] 상태에서 [%s] 상태로 변경 할 수 없습니다.", this.getLabel(), next.getLabel()));
        }
    }
}

