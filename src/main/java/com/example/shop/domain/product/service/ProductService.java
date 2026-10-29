package com.example.shop.domain.product.service;

import com.example.shop.domain.product.dto.ProductRequest;
import com.example.shop.domain.product.dto.ProductResponse;
import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.entity.ProductStatus;
import com.example.shop.domain.product.repository.ProductRepository;
import com.example.shop.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-24
 * @description 상품 생성/수정/삭제/목록 조회/ 상세조회 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(ProductRequest request)
    {
        Product product = new Product(request.getProductName(), request.getProductPrice(), request.getProductStock(), request.getProductCategory(),
                                      request.getUnit(), request.getOrigin(), request.getStorageMethod(), request.getDescription());

        productRepository.save(product);

        return product;
    }

    @Transactional
    public Product updateProduct(Long productId, ProductRequest request)
    {
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            log.warn("상품 수정 실패 - 존재하지 않는 productId: {}", productId);
            return new RuntimeException("해당 상품을 찾을 수 없습니다. 목록을 새로고침 후 다시 시도해주세요.");
        });

        product.update(request.getProductName(), request.getProductPrice(), request.getProductStock(), request.getProductCategory(),
                       request.getUnit(), request.getOrigin(), request.getStorageMethod(), request.getDescription());

        return product;
    }

    @Transactional
    public Product changeProductStatus(Long productId, ProductStatus status)
    {
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            log.warn("상품 판매 상태 변경 실패 - 존재하지 않는 productId: {}", productId);
            return new RuntimeException("해당 상품을 찾을 수 없습니다. 목록을 새로고침 후 다시 시도해주세요.");
        });

        if (status == ProductStatus.STOPPED) product.stopSale();
        else product.resumeSale();

        return product;
    }

    public PageResponse<ProductResponse> getAllProducts(ProductCategory category, Pageable pageable)
    {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "name", "id"));
        Page<Product> page;
        //판매중지 상품 안보이게 - 관리자/고객 분리
        if (category == null) page = productRepository.findAll(sortedPageable);
        else page = productRepository.findByCategory(category, sortedPageable);

        return new PageResponse<>(page.map(ProductResponse::new));
    }

    public PageResponse<ProductResponse> getSaleProductList(ProductCategory category, String sort, String keyword, Pageable pageable)
    {
        Page<Product> page;

        Sort sorting = switch (sort)
        {
            case "priceAsc" -> Sort.by("price", "name", "id").ascending();
            case "priceDesc" -> Sort.by("price").descending().and(Sort.by("name", "id"));
            default -> Sort.by("name", "id").ascending();
        };

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);

        if (category == null) page = productRepository.findByStatusAndNameContaining(ProductStatus.ON_SALE, keyword, sortedPageable);
        else page = productRepository.findByStatusAndCategoryAndNameContaining(ProductStatus.ON_SALE, category, keyword, sortedPageable);

        return new PageResponse<>(page.map(ProductResponse::new));
    }

    public Product getProduct(Long productId)
    {
        return productRepository.findById(productId).orElseThrow(() -> {
            log.warn("상품 상세 조회 실패 - 존재하지 않는 productId: {}", productId);
            return new RuntimeException("해당 상품을 찾을 수 없습니다.");
        });
    }

    public List<Product> getRandomProducts()
    {
        List<Product> allProducts = productRepository.findByStatus(ProductStatus.ON_SALE);
        Collections.shuffle(allProducts);
        return allProducts.stream().limit(3).toList();
    }
}
