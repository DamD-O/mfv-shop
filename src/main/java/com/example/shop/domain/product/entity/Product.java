package com.example.shop.domain.product.entity;

import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @date 2026-07-27
 * @description 상품 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock", nullable = false) //재고
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 30)
    private ProductCategory category;

    public Product(String name, int price, int stock, ProductCategory category)
    {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    @Override
    public String toString()
    {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", stock=" + stock + ", category=" + category + '}';
    }

    public void decreaseStock(int quantity)
    {
        if (this.stock < quantity)
        {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity)
    {
        this.stock += quantity;
    }

    public void update(String name, int price, int stock, ProductCategory category)
    {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }
}
