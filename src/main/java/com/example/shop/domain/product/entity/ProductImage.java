/**
 * @author yedam
 * @date 2026-07-27
 * @description 상품 이미지 Entity
 */
package com.example.shop.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_image")
public class ProductImage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ProductImage(Product product, String imagePath)
    {
        this.product = product;
        this.imagePath = imagePath;
        this.createdAt = LocalDateTime.now();
    }
}
