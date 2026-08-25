package com.example.shop.domain.product.controller;

import com.example.shop.domain.product.dto.ProductCreateRequest;
import com.example.shop.domain.product.dto.ProductResponse;
import com.example.shop.domain.product.dto.ProductStatusUpdateRequest;
import com.example.shop.domain.product.dto.ProductUpdateRequest;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-24
 * @description 상품 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    //상품 등록, 수정, 상태변경,전체목록 - /api/admin/products
    //판매중 목록, 상세조회 - /api/products

    //상품 생성
    @PostMapping("/admin/products")
    public ProductResponse createProduct(@Valid @RequestBody ProductCreateRequest request)
    {
        return new ProductResponse(productService.createProduct(request));
    }

    //상품 수정
    @PutMapping("/admin/products/{id}")
    public ProductResponse updateProduct(@Valid @RequestBody ProductUpdateRequest request, @PathVariable Long id)
    {
        return new ProductResponse(productService.updateProduct(id, request));
    }


    //상품 상태변경 - 부분변경
    @PatchMapping("/admin/products/{id}/status")
    public ProductResponse updateProductStatus(@RequestBody ProductStatusUpdateRequest request, @PathVariable Long id)
    {
        return new ProductResponse(productService.changeProductStatus(id, request.getStatus()));
    }

    //상품 전체목록 - 관리자 GET
    @GetMapping("/admin/products")
    public List<ProductResponse> getProducts(@RequestParam(required = false) ProductCategory category)
    {
        return productService.getAllProducts(category).stream().map(ProductResponse::new).toList();
    }

    //판매중 목록 - 사용자 GET
    @GetMapping("/products")
    public List<ProductResponse> getSaleProductList(@RequestParam(required = false) ProductCategory category)
    {
        return productService.getSaleProductList(category).stream().map(ProductResponse::new).toList();
    }

    //상품 상세조회 GET
    @GetMapping("/products/{id}")
    public ProductResponse getProductById(@PathVariable Long id)
    {
        return new ProductResponse(productService.getProduct(id));
    }
}
