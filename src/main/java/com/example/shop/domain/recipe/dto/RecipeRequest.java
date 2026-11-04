package com.example.shop.domain.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author madey
 * @DATE 2026-09-09
 * @description AI 질문 담는 DTO
 */

@Getter
@NoArgsConstructor
public class RecipeRequest {

    @NotBlank(message = "질문을 입력해주세요.")
    @Length(max = 200, message = "200자 이내로 입력해주세요.")
    private String question;

    public RecipeRequest(String question)
    {
        this.question = question;
    }
}
