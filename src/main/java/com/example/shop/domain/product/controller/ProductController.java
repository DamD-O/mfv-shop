package com.example.shop.domain.product.controller;

import com.example.shop.domain.product.dto.ProductCreateRequest;
import com.example.shop.domain.product.dto.ProductResponse;
import com.example.shop.domain.product.dto.ProductStatusUpdateRequest;
import com.example.shop.domain.product.dto.ProductUpdateRequest;
import com.example.shop.domain.product.entity.ProductCategory;
import com.example.shop.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "상품", description = "상품 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 등록")
    @PostMapping("/admin/products")
    public ProductResponse createProduct(@Valid @RequestBody ProductCreateRequest request)
    {
        return new ProductResponse(productService.createProduct(request));
    }

    @Operation(summary = "상품 수정")
    @PutMapping("/admin/products/{productId}")
    public ProductResponse updateProduct(@Valid @RequestBody ProductUpdateRequest request, @PathVariable Long productId)
    {
        return new ProductResponse(productService.updateProduct(productId, request));
    }

    @Operation(summary = "상품 상태변경")
    @PatchMapping("/admin/products/{productId}/status")
    public ProductResponse updateProductStatus(@RequestBody ProductStatusUpdateRequest request, @PathVariable Long productId)
    {
        return new ProductResponse(productService.changeProductStatus(productId, request.getStatus()));
    }

    @Operation(summary = "관리자용 상품 전체 목록 조회")
    @GetMapping("/admin/products")
    public List<ProductResponse> getProducts(@RequestParam(required = false) ProductCategory category)
    {
        return productService.getAllProducts(category).stream().map(ProductResponse::new).toList();
    }

    @Operation(summary = "판매 중 목록 조회")
    @GetMapping("/products")
    public List<ProductResponse> getSaleProductList(@RequestParam(required = false) ProductCategory category)
    {
        return productService.getSaleProductList(category).stream().map(ProductResponse::new).toList();
    }

    @Operation(summary = "상품 상세 조회")
    @GetMapping("/products/{productId}")
    public ProductResponse getProductById(@PathVariable Long productId)
    {
        return new ProductResponse(productService.getProduct(productId));
    }
}
