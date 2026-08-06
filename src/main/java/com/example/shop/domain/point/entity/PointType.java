/**
 * @author yedam
 * @date 2026-07-27
 * @description 포인트 구분 Enum (적립/사용)
 */
package com.example.shop.domain.point.entity;

public enum PointType {
    EARN(""),
    USE("");

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
