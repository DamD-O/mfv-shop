/**
 * @author yedam
 * @date 2026-07-27
 * @description 상품 이미지 Entity
 */
package com.example.shop.domain.product.entity;

import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_image")
public class ProductImage extends BaseTimeEntity
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

    public ProductImage(Product product, String imagePath)
    {
        this.product = product;
        this.imagePath = imagePath;
    }

    public void changeImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }
}
