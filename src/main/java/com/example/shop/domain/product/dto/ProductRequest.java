package com.example.shop.domain.product.dto;

import com.example.shop.domain.product.entity.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author madey
 * @DATE 2026-08-24
 * @description 상품 생성/수정 요청 DTO
 */

@Getter
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "상품명을 입력해주세요.")
    @Length(max = 500, message = "상품명은 500자 이내로 입력해주세요.")
    private String productName;

    @NotNull(message = "카테고리를 선택해주세요.")
    private ProductCategory productCategory;

    @NotNull(message = "상품 가격을 입력해주세요.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private Integer productPrice;

    @NotNull(message = "재고를 입력해주세요.")
    @PositiveOrZero(message = "재고는 0 이상이어야 합니다.")
    private Integer productStock;

    @NotBlank(message = "판매 단위를 입력해주세요.")
    @Length(max = 20, message = "단위는 20자 이내로 입력해주세요.")
    private String unit;

    @NotBlank(message = "원산지를 입력해주세요.")
    @Length(max = 100, message = "원산지는 100자 이내로 입력해주세요.")
    private String origin;

    @Length(max = 200, message = "보관방법은 200자 이내로 입력해주세요.")
    private String storageMethod;

    @Length(max = 5000, message = "설명은 5000자 이내로 입력해주세요.")
    private String description;

    public ProductRequest(String productName, Integer productPrice, Integer productStock, ProductCategory productCategory, String unit, String origin, String storageMethod, String description)
    {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productCategory = productCategory;
        this.unit = unit;
        this.origin = origin;
        this.storageMethod = storageMethod;
        this.description = description;
    }

}
