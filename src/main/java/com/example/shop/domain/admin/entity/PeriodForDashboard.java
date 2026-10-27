package com.example.shop.domain.admin.entity;

import lombok.Getter;

/**
 * @author madey
 * @DATE 2026-10-27
 * @description 관리페이지 대시보드 프리셋 기간 Enum
 */

@Getter
public enum PeriodForDashboard {
    ONE_MONTH(1),
    THREE_MONTHS(3),
    SIX_MONTHS(6),
    ONE_YEAR(12);

    private final int months;

    PeriodForDashboard(int months)
    {
        this.months = months;
    }

}
