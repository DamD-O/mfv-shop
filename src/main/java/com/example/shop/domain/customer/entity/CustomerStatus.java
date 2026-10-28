package com.example.shop.domain.customer.entity;

/**
 * @author madey
 * @DATE 2026-10-28
 * @description 관리자 페이지 고객 상태별 목록 필터
 */

public enum CustomerStatus {
    ACTIVE("활성", false),
    WITHDRAWN("탈퇴", true);

    private final String label;
    private final boolean withdrawn;

    CustomerStatus(String label, boolean withdrawn)
    {
        this.label = label;
        this.withdrawn = withdrawn;
    }

    public static String getStatusLabel(boolean withdrawn)
    {
        if (withdrawn) return CustomerStatus.WITHDRAWN.label;
        else return CustomerStatus.ACTIVE.label;
    }

    public String getLabel()
    {
        return label;
    }

    public boolean isWithdrawn()
    {
        return withdrawn;
    }
}