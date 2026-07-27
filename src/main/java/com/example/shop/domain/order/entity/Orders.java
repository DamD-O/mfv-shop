/**
 * @author yedam
 * @date 2026-07-27
 * @description 주문 Entity
 */
package com.example.shop.domain.order.entity;

import com.example.shop.domain.address.entity.DeliveryAddress;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Orders extends BaseTimeEntity
{
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
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Orders(Customer customer, DeliveryAddress deliveryAddress, String addressSnapshot, int totalAmount)
    {
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.addressSnapshot = addressSnapshot;
        this.totalAmount = totalAmount;
        this.orderStatus = OrderStatus.PAYMENT_PENDING;
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
