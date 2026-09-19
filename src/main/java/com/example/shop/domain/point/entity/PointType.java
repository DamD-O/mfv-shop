package com.example.shop.domain.point.entity;

/**
 * @author madey
 * @date 2026-07-27
 * @description 포인트 구분 Enum (적립/사용)
 */

public enum PointType {
    EARN("적립"),
    USE("사용"),
    CANCEL("취소");

    private final String label;

    PointType(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}
