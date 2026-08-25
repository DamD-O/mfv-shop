package com.example.shop.domain.product.dto;

import com.example.shop.domain.product.entity.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-25
 * @description 상품 상태 수정 요청 DTO
 */

@Getter
@NoArgsConstructor
public class ProductStatusUpdateRequest {

     @NotNull(message = "판매 상태를 선택해주세요.")
    private ProductStatus status;

    public ProductStatusUpdateRequest(ProductStatus status)
    {
        this.status = status;
    }

}
