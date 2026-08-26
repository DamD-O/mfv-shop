package com.example.shop.domain.order.service;

import com.example.shop.domain.address.entity.DeliveryAddress;
import com.example.shop.domain.address.repository.DeliveryAddressRepository;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.order.dto.OrderItemRequest;
import com.example.shop.domain.order.dto.OrderRequest;
import com.example.shop.domain.order.entity.OrderDetail;
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
                return new RuntimeException("존재하지 않는 상품입니다.");
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
            return new RuntimeException("회원 정보를 찾을 수 없습니다.");
        });

        Orders orders = new Orders(customer, address, addressSnapshot, totalAmount);

        //주문 - 상세주문 매핑
        for (OrderDetail orderDetail : orderDetails)
            orders.addOrderDetail(orderDetail);

        ordersRepository.save(orders);

        return orders;
    }
}
