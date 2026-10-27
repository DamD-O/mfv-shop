package com.example.shop.domain.admin.service;

import com.example.shop.domain.admin.dto.OrderStatsResponse;
import com.example.shop.domain.admin.dto.SalesProductRankResponse;
import com.example.shop.domain.admin.entity.DateGroupingUnit;
import com.example.shop.domain.admin.entity.PeriodForDashboard;
import com.example.shop.domain.admin.repository.AdminRepository;
import com.example.shop.domain.admin.dto.CustomerStatsResponse;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.order.dto.OrderStatsProjection;
import com.example.shop.domain.order.repository.OrderDetailRepository;
import com.example.shop.domain.order.repository.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description 관리자 서비스
 */

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;

    public String getAdminName(String adminId)
    {
        return adminRepository.findOneByAdminId(adminId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다.")).getName();
    }

    /**
     *
     * @param period 조회 기간(1/3/6/12개월)
     * @param unit 일/월별 단위
     * @return 주문 수 추이
     */
    public List<OrderStatsResponse> getOrderStats(PeriodForDashboard period, DateGroupingUnit unit)
    {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(period.getMonths());
        startDate = unit == DateGroupingUnit.MONTH ? startDate.withDayOfMonth(1) : startDate;
        Map<String, Long> orderStatData = new HashMap<>();

        for (OrderStatsProjection data : ordersRepository.findDateDeliveredCounts(unit.getPattern(), startDate.atStartOfDay()))
        {
            orderStatData.put(data.getBaseDate(), data.getOrderCount());
        }

        List<OrderStatsResponse> orderStatsList = new ArrayList<>();

        //데이터가 없는 날짜는 0으로 세팅하기
        if (unit == DateGroupingUnit.DAY)
        {
            for (LocalDate d = startDate; !d.isAfter(today); d = d.plusDays(1))
            {
                String key = d.toString();

                orderStatsList.add(new OrderStatsResponse(key, orderStatData.getOrDefault(key, 0L)));
            }
        }
        else if (unit == DateGroupingUnit.MONTH)
        {
            YearMonth startMonth = YearMonth.from(startDate);

            for (YearMonth m = startMonth; !m.isAfter(YearMonth.from(today)); m = m.plusMonths(1))
            {
                String key = m.toString();
                orderStatsList.add(new OrderStatsResponse(key, orderStatData.getOrDefault(key, 0L)));
            }
        }

        return orderStatsList;
    }

    /**
     *
     * @param period 조회 기간
     * @return 상품 판매량 순위
     */
    public List<SalesProductRankResponse> getSalesProductRank(PeriodForDashboard period)
    {
        LocalDate today = LocalDate.now();
        LocalDateTime startDate = today.minusMonths(period.getMonths()).atStartOfDay();

        return orderDetailRepository.findProductSalesRank(startDate).stream().map(SalesProductRankResponse::new).toList();
    }

    /**
     * @return 회원 수 (전체 / 활성 / 탈퇴)
     */
    public CustomerStatsResponse getCustomerStats()
    {
        Long totalCount = customerRepository.count();
        Long activeCount = customerRepository.countByWithdrawnFalse();
        Long withdrawnCount = customerRepository.countByWithdrawnTrue();

        return new CustomerStatsResponse(totalCount, activeCount, withdrawnCount);
    }
}
