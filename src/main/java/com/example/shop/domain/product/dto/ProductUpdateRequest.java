package com.example.shop.domain.product.dto;

import com.example.shop.domain.product.entity.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-24
 * @description 상품 수정 요청 DTO
 */

@Getter
@NoArgsConstructor
public class ProductUpdateRequest {

    @NotBlank(message = "상품명을 입력해주세요.")
    private String productName;

    @NotNull(message = "상품 가격을 입력해주세요.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private Integer productPrice;

    @NotNull(message = "재고를 입력해주세요.")
    @PositiveOrZero(message = "재고는 0 이상이어야 합니다.")
    private Integer productStock;

    @NotNull(message = "카테고리를 선택해주세요.")
    private ProductCategory productCategory;

    @NotBlank(message = "판매 단위를 입력해주세요.")
    private String unit;

    @NotBlank(message = "원산지를 입력해주세요.")
    private String origin;

    @NotBlank(message = "보관방법을 입력해주세요.")
    private String storageMethod;

    @NotBlank(message = "상품 설명을 입력해주세요.")
    private String description;

    public ProductUpdateRequest(Integer productPrice, Integer productStock, ProductCategory productCategory, String unit, String origin, String storageMethod, String description)
    {
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productCategory = productCategory;
        this.unit = unit;
        this.origin = origin;
        this.storageMethod = storageMethod;
        this.description = description;
    }
}
