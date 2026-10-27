package com.example.shop.domain.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-10-27
 * @description 관리자 통계용 응답 dto
 */

@Getter
@NoArgsConstructor
public class OrderStatsResponse {
    private String date;
    private long count;

    public OrderStatsResponse(String date, long count)
    {
        this.date = date;
        this.count = count;
    }
}
