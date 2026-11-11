package com.example.shop.domain.product.repository;

import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.entity.ProductStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    List<Product> findByStatusAndStockGreaterThan(ProductStatus status, int stock);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findWithLockByIdAndStatus(Long id, ProductStatus status);

    /**
     *
     * @param productId
     * @param quantity  현재 재고에 취소 재고 원복하게
     */
    @Modifying
    @Query("UPDATE Product p SET p.stock = p.stock + :quantity WHERE p.id = :productId")
    void restoreProductStock(@Param("productId") Long productId, @Param("quantity") int quantity);
}
