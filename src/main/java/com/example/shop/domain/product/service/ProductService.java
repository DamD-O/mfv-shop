package com.example.shop.domain.product.service;

import com.example.shop.domain.product.dto.ProductCreateRequest;
import com.example.shop.domain.product.dto.ProductUpdateRequest;
import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.entity.ProductStatus;
import com.example.shop.domain.product.repository.ProductImageRepository;
import com.example.shop.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ProductImageRepository productImgRepository;

    //상품 생성
    @Transactional
    public Product createProduct(ProductCreateRequest request)
    {
        Product product = new Product(request.getProductName(),
                                      request.getProductPrice(),
                                      request.getProductStock(),
                                      request.getProductCategory(),
                                      request.getUnit());

        productRepository.save(product);

        return product;
    }

    //상품 수정
    @Transactional
    public Product updateProduct(Long productId, ProductUpdateRequest request)
    {
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            log.warn("상품 수정 실패 - 존재하지 않는 productId: {}", productId);
            return new RuntimeException("해당 상품을 찾을 수 없습니다. 목록을 새로고침 후 다시 시도해주세요.");
        });

        product.update(request.getProductName(),
                       request.getProductPrice(),
                       request.getProductStock(),
                       request.getProductCategory(),
                       request.getUnit());

        return product;
    }

    //상품 판매상태 변경
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

    //판매중지 상품 안보이게 - 관리자/고객 분리
    public List<Product> getAllProducts(ProductCategory category)
    {
        //카테고리 별 검색
        if (category == null)
            return productRepository.findAll();

        return productRepository.findByCategory(category);
    }

    public List<Product> getSaleProductList(ProductCategory category)
    {
        if (category == null)
            return productRepository.findByStatus(ProductStatus.ON_SALE);

        return productRepository.findByStatusAndCategory(ProductStatus.ON_SALE, category);

    }

    //상품 상세조회
    public Product getProduct(Long productId)
    {
        return productRepository.findById(productId).orElseThrow(() -> {
            log.warn("상품 조회 실패 - 존재하지 않는 productId: {}", productId);
            return new RuntimeException("해당 상품을 찾을 수 없습니다.");
        });
    }
}
