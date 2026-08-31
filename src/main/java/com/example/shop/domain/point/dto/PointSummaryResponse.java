package com.example.shop.domain.point.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description 포인트 요약 응답 DTO
 */

@Getter
@NoArgsConstructor
public class PointSummaryResponse {
    private int availPoint;
    private List<PointHistoryResponse> historyList;

    public PointSummaryResponse(int availPoint, List<PointHistoryResponse> historyList)
    {
        this.availPoint = availPoint;
        this.historyList = historyList;
    }
}
