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

    //todo: 결제 시점 주문 상태 변경 : PAYMENT_COMPLETE

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

        Orders orders = new Orders(customer, address, addressSnapshot, totalAmount);

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

        orders.changeStatus(OrderStatus.CANCELED);
    }
}
