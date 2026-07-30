/**
 * @author yedam
 * @date 2026-07-27
 * @description 주문상세 Entity
 */
package com.example.shop.domain.order.entity;

import com.example.shop.domain.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_detail")
public class OrderDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false) //수량
    private int quantity;

    @Column(name = "price_at_order", nullable = false) // 주문시점 가격
    private int priceAtOrder;

    public OrderDetail(Product product, int quantity, int priceAtOrder)
    {
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    @Override
    public String toString()
    {
        return "OrderDetail{" + "id=" + id + ", orders=" + orders + ", product=" + product + ", quantity=" + quantity + ", priceAtOrder=" + priceAtOrder + '}';
    }

    void assignOrder(Orders orders)
    {
        this.orders = orders;
    }
}
