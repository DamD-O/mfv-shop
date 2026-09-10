package com.example.shop.domain.point.controller;

import com.example.shop.domain.point.dto.PointSummaryResponse;
import com.example.shop.domain.point.service.PointHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description
 */

@Tag(name = "포인트", description = "포인트 이력 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class PointHistoryController {
    private final PointHistoryService service;

    @Operation(summary = "포인트 이력 조회")
    @GetMapping("/points")
    public PointSummaryResponse getPointHistoryList(Authentication authentication)
    {
        return service.getPointSummary(authentication.getName());
    }
}
