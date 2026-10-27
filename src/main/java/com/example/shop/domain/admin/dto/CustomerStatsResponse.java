package com.example.shop.domain.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-10-27
 * @description 사용자 통계 응답 DTO
 */

@Getter
@NoArgsConstructor
public class CustomerStatsResponse {
    private Long totalCount;
    private Long activeCount;
    private Long withdrawnCount;

    public CustomerStatsResponse(Long totalCount, Long activeCount, Long withdrawnCount)
    {
        this.totalCount = totalCount;
        this.activeCount = activeCount;
        this.withdrawnCount = withdrawnCount;
    }
}
