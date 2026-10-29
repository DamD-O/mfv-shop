package com.example.shop.domain.admin.service;

import com.example.shop.domain.admin.dto.CustomerStatsResponse;
import com.example.shop.domain.admin.dto.OrderStatsResponse;
import com.example.shop.domain.admin.dto.SalesProductRankResponse;
import com.example.shop.domain.admin.entity.DateGroupingUnit;
import com.example.shop.domain.admin.entity.PeriodForDashboard;
import com.example.shop.domain.admin.repository.AdminRepository;
import com.example.shop.domain.customer.dto.CustomerSelectResponse;
import com.example.shop.domain.customer.dto.UpdateCustomerNameRequest;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.entity.CustomerStatus;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.order.dto.OrderStatsProjection;
import com.example.shop.domain.order.repository.OrderDetailRepository;
import com.example.shop.domain.order.repository.OrdersRepository;
import com.example.shop.global.dto.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description 관리자 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
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
     * @param unit   일/월별 단위
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

    public PageResponse<CustomerSelectResponse> getCustomerList(CustomerStatus status, Pageable pageable)
    {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "name", "customerId"));
        Page<Customer> customerPage;

        if (status != null) customerPage = customerRepository.findByWithdrawn(status.isWithdrawn(), sortedPageable);
        else customerPage = customerRepository.findAll(sortedPageable);

        return new PageResponse<>(customerPage.map(CustomerSelectResponse::new));
    }

    public CustomerSelectResponse getCustomerById(String customerId)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.warn("회원 상세 정보 조회 실패 - 존재하지 않는 사용자 입니다. customerId : {}", customerId);
            return new RuntimeException("회원 상세 정보 조회 실패 : " + customerId + " 는 존재하지 않는 사용자입니다.");
        });

        return new CustomerSelectResponse(customer);
    }

    @Transactional
    public CustomerSelectResponse updateCustomerName(String customerId, UpdateCustomerNameRequest request)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.warn("회원 이름 변경 실패 - 존재하지 않는 사용자 입니다. customerId : {}", customerId);
            return new RuntimeException("회원 이름 변경 실패 : " + customerId + " 는 존재하지 않는 사용자입니다.");
        });

        if (customer.isWithdrawn()) throw new RuntimeException("탈퇴한 사용자는 이름변경이 불가 합니다.");

        customer.updateCustomerName(request.getCustomerName().trim());

        return new CustomerSelectResponse(customer);
    }
}