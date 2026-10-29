package com.example.shop.domain.product.repository;

import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByStatusAndCategoryAndNameContaining(ProductStatus status, ProductCategory category, String keyword, Pageable pageable);

    Page<Product> findByStatusAndNameContaining(ProductStatus status, String keyword, Pageable pageable);

    Page<Product> findByCategory(ProductCategory category, Pageable pageable);

    List<Product> findByStatus(ProductStatus status);

    Optional<Product> findByIdAndStatus(Long id, ProductStatus status);
}
