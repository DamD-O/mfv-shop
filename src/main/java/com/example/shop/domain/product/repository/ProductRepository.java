package com.example.shop.domain.product.repository;

import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByCategory(ProductCategory category);
    List<Product> findByStatusAndCategory(ProductStatus status, ProductCategory category);
}
