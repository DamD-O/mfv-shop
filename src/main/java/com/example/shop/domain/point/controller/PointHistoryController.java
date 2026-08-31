package com.example.shop.domain.point.controller;

import com.example.shop.domain.point.dto.PointSummaryResponse;
import com.example.shop.domain.point.service.PointHistoryService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class PointHistoryController {
    private final PointHistoryService service;

    @GetMapping("/points")
    public PointSummaryResponse getPointHistoryList(Authentication authentication)
    {
        return service.getPointSummary(authentication.getName());
    }
}
