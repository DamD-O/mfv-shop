package com.example.shop.domain.point.service;

import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.point.dto.PointHistoryResponse;
import com.example.shop.domain.point.dto.PointSummaryResponse;
import com.example.shop.domain.point.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;
    private final CustomerRepository customerRepository;

    //사용자 포인트 이력 및 사용가능한 포인트
    public PointSummaryResponse getPointSummary(String customerId)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.warn("포인트 조회 실패 - 존재하지 않는 customerId : {}", customerId);
            return new RuntimeException("회원 정보를 찾을 수 없습니다.");
        });

        List<PointHistoryResponse> history = pointHistoryRepository.findByCustomer_CustomerIdOrderByCreatedAtDesc(customerId)
                                                                   .stream()
                                                                   .map(PointHistoryResponse::new)
                                                                   .toList();

        return new PointSummaryResponse(customer.getPoint(), history);
    }
}
