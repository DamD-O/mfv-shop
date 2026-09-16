package com.example.shop.domain.order.entity;

import com.example.shop.domain.address.entity.DeliveryAddress;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author madey
 * @date 2026-07-27
 * @description 주문 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private DeliveryAddress deliveryAddress;

    @Column(name = "address_snapshot", nullable = false) //주문 시점 주소
    private String addressSnapshot;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 20)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<OrderDetail> orderDetails = new ArrayList<>();

    @Column(name = "use_point", nullable = false) //주문시 사용할 포인트
    private int usePoint;

    @Column(name = "delivery_request", columnDefinition = "TEXT")
    private String deliveryRequest; // 배송 요청사항

    public Orders(Customer customer, DeliveryAddress deliveryAddress, String addressSnapshot, int totalAmount, int usePoint, String deliveryRequest)
    {
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.addressSnapshot = addressSnapshot;
        this.totalAmount = totalAmount;
        this.orderStatus = OrderStatus.PAYMENT_PENDING;
        this.usePoint = usePoint;
        this.deliveryRequest = deliveryRequest;
    }

    public void addOrderDetail(OrderDetail orderDetail)
    {
        this.orderDetails.add(orderDetail);
        orderDetail.assignOrder(this);
    }

    public void changeStatus(OrderStatus status)
    {
        this.orderStatus = status;
    }
}
