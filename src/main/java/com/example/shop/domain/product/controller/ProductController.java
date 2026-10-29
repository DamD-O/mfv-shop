package com.example.shop.domain.product.controller;

import com.example.shop.domain.product.dto.ProductRequest;
import com.example.shop.domain.product.dto.ProductResponse;
import com.example.shop.domain.product.dto.ProductStatusUpdateRequest;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.service.ProductService;
import com.example.shop.global.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-24
 * @description 상품 컨트롤러
 */

@Tag(name = "상품", description = "상품 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 등록")
    @PostMapping("/admin/products")
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request)
    {
        return new ProductResponse(productService.createProduct(request));
    }

    @Operation(summary = "상품 수정")
    @PutMapping("/admin/products/{productId}")
    public ProductResponse updateProduct(@Valid @RequestBody ProductRequest request, @PathVariable Long productId)
    {
        return new ProductResponse(productService.updateProduct(productId, request));
    }

    @Operation(summary = "상품 상태변경")
    @PatchMapping("/admin/products/{productId}/status")
    public ProductResponse updateProductStatus(@Valid @RequestBody ProductStatusUpdateRequest request, @PathVariable Long productId)
    {
        return new ProductResponse(productService.changeProductStatus(productId, request.getStatus()));
    }

    @Operation(summary = "관리자용 상품 전체 목록 조회")
    @GetMapping("/admin/products")
    public PageResponse<ProductResponse> getProducts(@RequestParam(required = false) ProductCategory category, @PageableDefault(size = 10) Pageable pageable)
    {
        return productService.getAllProducts(category, pageable);
    }

    @Operation(summary = "판매 중 목록 조회")
    @GetMapping("/products")
    public PageResponse<ProductResponse> getSaleProductList(@RequestParam(required = false) ProductCategory category, @RequestParam(required = false, defaultValue = "name") String sort, @RequestParam(defaultValue = "") String keyword, @PageableDefault(size = 3) Pageable pageable)
    {
        return productService.getSaleProductList(category, sort, keyword.trim(), pageable);
    }

    @Operation(summary = "상품 상세 조회")
    @GetMapping("/products/{productId}")
    public ProductResponse getProductById(@PathVariable Long productId)
    {
        return new ProductResponse(productService.getProduct(productId));
    }

    @Operation(summary = "랜덤 상품 조회")
    @GetMapping("/products/random")
    public List<ProductResponse> getRandomProducts()
    {
        return productService.getRandomProducts().stream().map(ProductResponse::new).toList();
    }
}
