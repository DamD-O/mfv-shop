package com.example.shop.domain.order.service;

import com.example.shop.domain.address.entity.DeliveryAddress;
import com.example.shop.domain.address.repository.DeliveryAddressRepository;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.order.dto.OrderItemRequest;
import com.example.shop.domain.order.dto.OrderRequest;
import com.example.shop.domain.order.entity.OrderDetail;
import com.example.shop.domain.order.entity.OrderStatus;
import com.example.shop.domain.order.entity.Orders;
import com.example.shop.domain.order.repository.OrdersRepository;
import com.example.shop.domain.point.entity.PointHistory;
import com.example.shop.domain.point.entity.PointType;
import com.example.shop.domain.point.repository.PointHistoryRepository;
import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-26
 * @description 주문 서비스
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final CustomerRepository customerRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public Orders createOrders(String customerId, OrderRequest request)
    {
        //배송지 조회 + 주소 스냅샷 생성
        DeliveryAddress address = deliveryAddressRepository.findById(request.getDeliveryId()).orElseThrow(() -> {
            log.warn("deliveryAddress not found");
            return new RuntimeException("존재하지 않는 배송지 입니다. 다시 선택해주세요.");
        });

        //주소 스냅샷 - 수령인(연락처) / 배송지(우편번호)
        String addressSnapshot = address.getReceiver() + "(" + address.getContact() + ") / " + address.getRoadAddress() + " " + address.getDetailAddress() + " (" + address.getZipcode() + ")";

        //상품별 재고 차감 및 주문상세 생성
        int totalAmount = 0;

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderItemRequest item : request.getOrderItemRequest())
        {
            Long productId = item.getProductId();
            Product product = productRepository.findById(productId).orElseThrow(() -> {
                log.warn("주문 생성 - 존재하지 않는 상품입니다. productId : {}", productId);
                return new RuntimeException("존재하지 않는 상품으로 주문 실패");
            });

            product.decreaseStock(item.getQuantity()); //재고 차감

            //상세 주문 생성
            OrderDetail detail = new OrderDetail(product, item.getQuantity(), product.getPrice());
            orderDetails.add(detail);

            //총 가격
            totalAmount += product.getPrice() * item.getQuantity();
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.warn("주문 생성 실패 - 존재하지 않는 customerId: {}", customerId);
            return new RuntimeException("회원 정보를 찾을 수 없으므로, 주문 실패하였습니다.");
        });

        //포인트 사용
        Integer usePoint = request.getUsePoint() != null ? request.getUsePoint() : 0;

        if (usePoint > customer.getPoint()) throw new RuntimeException("보유 포인트가 부족합니다.");

        if (usePoint > totalAmount) throw new RuntimeException("사용 포인트가 상품 금액을 초과 할 수 없습니다.");

        int finalAmount = totalAmount - usePoint;

        Orders orders = new Orders(customer, address, addressSnapshot, finalAmount, usePoint, request.getDeliveryRequest());

        //주문 - 상세주문 매핑
        for (OrderDetail orderDetail : orderDetails)
            orders.addOrderDetail(orderDetail);

        ordersRepository.save(orders);

        return orders;
    }

    //주문 목록 조회
    public List<Orders> getOrderList(String customerId)
    {
        return ordersRepository.findByCustomer_CustomerId(customerId);
    }

    //주문 상세조회
    public Orders getOrder(String customerId, Long orderId)
    {
        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> {
            log.warn("주문 상세조회 - 존재하지 않는 orderId: {}", orderId);
            return new RuntimeException("존재 하지 않는 주문으로 주문 상세조회 실패");
        });

        if (!orders.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인의 주문만 조회 가능합니다.");

        return orders;
    }

    //주문 상태 변경 - 관리자용
    @Transactional
    public Orders updateOrderStatus(Long orderId, OrderStatus status)
    {
        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> {
            log.warn("주문 상태변경 - 존재하지 않는 orderId: {}", orderId);
            return new RuntimeException("존재 하지 않는 주문으로 주문 상태변경 실패");
        });

        orders.changeStatus(status);

        return orders;
    }

    //주문 취소
    @Transactional
    public void cancelOrder(String customerId, Long orderId)
    {
        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> {
            log.warn("주문 취소 - 존재하지 않는 orderId: {}", orderId);
            return new RuntimeException("존재 하지 않는 주문으로 주문 취소 실패");
        });

        if (!orders.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인의 주문만 취소할 수 있습니다.");

        //취소 가능상태 체크
        OrderStatus status = orders.getOrderStatus();
        if (status != OrderStatus.PAYMENT_PENDING && status != OrderStatus.PAYMENT_COMPLETE)
        {
            throw new RuntimeException("배송 준비 중 이후 상태의 주문은 취소할 수 없습니다. \n 주문 상태 : " + status.getLabel());
        }

        //재고 원복
        for (OrderDetail detail : orders.getOrderDetails())
        {
            detail.getProduct().increaseStock(detail.getQuantity());
        }

        if (status == OrderStatus.PAYMENT_COMPLETE && orders.getUsePoint() > 0)
        {
            orders.getCustomer().earnPoint(orders.getUsePoint());
            pointHistoryRepository.save(new PointHistory(orders.getCustomer(), orders, PointType.CANCEL, orders.getUsePoint()));
        }

        orders.changeStatus(OrderStatus.CANCELED);
    }

    //가상 결제 : 포인트 1.5% 적립(소수점 버림), 결제 시뮬레이션: 65%
    @Transactional
    public Orders processPayment(String customerId, Long orderId)
    {
        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> {
            log.warn("결제 실패 - 존재하지 않는 주문 : {}", orderId);
            return new RuntimeException("결제 실패 - 존재하지 않는 주문입니다.");
        });

        if (!orders.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인의 주문만 결제할 수 있습니다.");

        //결제대기만 결제 가능 그외 예외처리
        if (!orders.getOrderStatus().equals(OrderStatus.PAYMENT_PENDING))
            throw new RuntimeException("결제 대기 상태의 주문만 결제 가능합니다. \n 주문 상태 : " + orders.getOrderStatus().getLabel());

        // 65% 성공확률
        boolean isSuccess = Math.random() < 0.65;

        //결제 성공 시 포인트 적립 및 사용, 상태변경 / 결제 실패 시 예외
        if (isSuccess)
        {
            //포인트 사용
            if (orders.getUsePoint() > 0)
            {
                orders.getCustomer().usePoint(orders.getUsePoint());
                PointHistory usePointHistory = new PointHistory(orders.getCustomer(), orders, PointType.USE, orders.getUsePoint());
                pointHistoryRepository.save(usePointHistory);
            }

            //포인트 적립
            int point = (int) (orders.getTotalAmount() * 0.015);
            orders.getCustomer().earnPoint(point);
            PointHistory pointHistory = new PointHistory(orders.getCustomer(), orders, PointType.EARN, point);
            pointHistoryRepository.save(pointHistory);

            //주문 상태 변경 - 결제 완료
            orders.changeStatus(OrderStatus.PAYMENT_COMPLETE);
        }
        else
        {
            throw new RuntimeException("결제에 실패했습니다. 다시 시도해주세요.");
        }

        return orders;
    }
}
