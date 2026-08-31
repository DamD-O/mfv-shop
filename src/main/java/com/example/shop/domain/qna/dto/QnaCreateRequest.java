package com.example.shop.domain.qna.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description QnA 생성 DTO
 */

@Getter
@NoArgsConstructor
public class QnaCreateRequest {

    @NotNull(message = "상품을 선택해주세요.")
    private Long productId;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "문의 내용을 입력해주세요.")
    private String content;

    public QnaCreateRequest(Long productId, String title, String content)
    {
        this.productId = productId;
        this.title = title;
        this.content = content;
    }
}
