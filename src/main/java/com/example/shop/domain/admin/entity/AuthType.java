package com.example.shop.domain.admin.entity;

import lombok.Getter;

/**
 * @author madey
 * @DATE 2026-07-29
 * @description 권한 타입 Enum
 */

@Getter
public enum AuthType {
    ADMIN("관리자"),
    USER("사용자");

    private String label;

    AuthType(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}
