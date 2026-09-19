package com.example.shop.domain.point.service;

import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.order.entity.OrderStatus;
import com.example.shop.domain.order.entity.Orders;
import com.example.shop.domain.order.repository.OrdersRepository;
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
    private final OrdersRepository ordersRepository;

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

        //결제대기 중인 주문에서 사용한 포인트를 사용가능한 포인트에서 제외
        int pendingUsePoint = ordersRepository.findByCustomer_CustomerIdAndOrderStatus(customerId, OrderStatus.PAYMENT_PENDING)
                                              .stream()
                                              .mapToInt(Orders::getUsePoint)
                                              .sum();

        int availPoint = customer.getPoint() - pendingUsePoint;

        return new PointSummaryResponse(availPoint, history);
    }
}
