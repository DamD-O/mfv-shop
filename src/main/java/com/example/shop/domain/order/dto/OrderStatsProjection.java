package com.example.shop.domain.order.dto;

/**
 * @author madey
 * @DATE 2026-10-27
 * @description 주문 통계 데이터 가져오는 프로젝션 - 기준날짜 : 카운트
 */
public interface OrderStatsProjection {
    String getBaseDate();
    Long getOrderCount();
}

