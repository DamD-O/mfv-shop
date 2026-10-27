package com.example.shop.domain.admin.entity;

import lombok.Getter;

/**
 * @author madey
 * @DATE 2026-10-27
 * @description 관리자 대시보드 날짜 묶는 기준 enum
 */
@Getter
public enum DateGroupingUnit {
    DAY("%Y-%m-%d"),
    MONTH("%Y-%m");

    private final String pattern;
    DateGroupingUnit(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
