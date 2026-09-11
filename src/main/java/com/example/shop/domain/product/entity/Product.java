package com.example.shop.domain.product.entity;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProductStatus status = ProductStatus.ON_SALE;

    //상품 단위
    @Column(name = "unit", nullable = false, length = 20)
    private String unit;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private final List<ProductImage> productImages = new ArrayList<>();

    // 원산지
    @Column(name = "origin", length = 100)
    private String origin;

    // 보관방법
    @Column(name = "storage_method", length = 200)
    private String storageMethod;

    // 상품 설명
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Product(String name, int price, int stock, ProductCategory category, String unit, String origin, String storageMethod, String description)
    {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.unit = unit;
        this.origin = origin;
        this.storageMethod = storageMethod;
        this.description = description;
    }

    //재고 수정
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

    //상품 수정
    public void update(String name, int price, int stock, ProductCategory category, String unit, String origin, String storageMethod, String description)
    {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.unit = unit;
        this.origin = origin;
        this.storageMethod = storageMethod;
        this.description = description;
    }

    //판매 중 / 판매 중지 상태 변경
    public void stopSale()
    {
        this.status = ProductStatus.STOPPED;
    }

    public void resumeSale()
    {
        this.status = ProductStatus.ON_SALE;
    }
}
