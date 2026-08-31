package com.example.shop.domain.qna.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description 문의내용 관리자 답변 요청 dto
 */

@Getter
@NoArgsConstructor
public class QnaAnswerRequest {
    @NotBlank(message = "문의 답변을 입력해주세요.")
    private String answer;

    public QnaAnswerRequest(String answer)
    {
        this.answer = answer;
    }
}
