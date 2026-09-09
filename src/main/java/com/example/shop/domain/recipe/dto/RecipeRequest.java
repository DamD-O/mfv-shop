package com.example.shop.domain.recipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-09-09
 * @description AI 질문 담는 DTO
 */

@Getter
@NoArgsConstructor
public class RecipeRequest {
    private String question;

    public RecipeRequest(String question)
    {
        this.question = question;
    }
}
