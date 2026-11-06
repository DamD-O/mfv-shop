package com.example.shop.domain.review.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-09-08
 * @description 리뷰작성 요청 DTO
 */

@Getter
@NoArgsConstructor
public class ReviewRequest {

    @NotNull(message = "리뷰를 작성할 상품을 선택해주세요.")
    private Long orderDetailId;

    @NotNull(message = "평점을 입력해주세요.")
    @Min(value = 1, message = "평점은 1점 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5점 이하여야 합니다.")
    private Integer rating;

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    @Size(min = 10, max = 1000, message = "리뷰 내용은 최소 10자 이상 1000자 이내여야 합니다.")
    private String content;

    public ReviewRequest(Long orderDetailId, Integer rating, String content)
    {
        this.orderDetailId = orderDetailId;
        this.rating = rating;
        this.content = content;
    }
}
