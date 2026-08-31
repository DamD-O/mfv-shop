package com.example.shop.domain.point.dto;

import com.example.shop.domain.point.entity.PointHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description 고객별 포인트 이력 응답 DTO
 */

@Getter
@NoArgsConstructor
public class PointHistoryResponse {
    private String type;
    private String typeLabel;
    private int point;
    private Long orderId;
    private LocalDateTime createdAt;

    public PointHistoryResponse(PointHistory pointHistory)
    {
        this.type = pointHistory.getType().name();
        this.typeLabel = pointHistory.getType().getLabel();
        this.point = pointHistory.getPoint();
        this.orderId = pointHistory.getOrders() != null ? pointHistory.getOrders().getId() : null;
        this.createdAt = pointHistory.getCreatedAt();
    }
}
