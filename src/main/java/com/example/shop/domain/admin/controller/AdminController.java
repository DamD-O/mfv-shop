package com.example.shop.domain.admin.controller;

import com.example.shop.domain.admin.dto.OrderStatsResponse;
import com.example.shop.domain.admin.entity.DateGroupingUnit;
import com.example.shop.domain.admin.entity.PeriodForDashboard;
import com.example.shop.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-10-27
 * @description 관리자 컨트롤러
 */
@Tag(name = "관리자", description = "관리페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "배송 완료 주문 수 추이", description = "대시보드 배송완료 주문 건수 통계")
    @GetMapping("/dashboard/order-stats")
    public List<OrderStatsResponse> getOrderStats(@RequestParam PeriodForDashboard period, @RequestParam DateGroupingUnit unit)
    {
        return adminService.getOrderStats(period, unit);
    }
}
