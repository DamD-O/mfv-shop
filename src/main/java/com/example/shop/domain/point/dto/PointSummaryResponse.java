package com.example.shop.domain.point.dto;

import com.example.shop.global.dto.PageResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description 포인트 요약 응답 DTO
 */

@Getter
@NoArgsConstructor
public class PointSummaryResponse {
    private int availPoint;
    private PageResponse<PointHistoryResponse> historyList;

    public PointSummaryResponse(int availPoint, PageResponse<PointHistoryResponse> historyList)
    {
        this.availPoint = availPoint;
        this.historyList = historyList;
    }
}
