package com.example.shop.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(min = 10, message = "리뷰 내용은 최소 10글자 이상이어야 합니다.")
    private String content;

    public ReviewRequest(Long orderDetailId, Integer rating, String content)
    {
        this.orderDetailId = orderDetailId;
        this.rating = rating;
        this.content = content;
    }
}
