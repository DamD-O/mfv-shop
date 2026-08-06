package com.example.shop.domain.product.repository;

import com.example.shop.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yedam
 * @DATE 2026-07-28
 * @description
 */
public interface ProductRepository extends JpaRepository<Product, Long> {}
