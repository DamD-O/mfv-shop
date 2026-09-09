package com.example.shop.domain.recipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-09-09
 * @description AI 응답을 담는 DTO
 */

@Getter
@NoArgsConstructor
public class RecipeResponse {
    private String answer;

    public RecipeResponse(String answer)
    {
        this.answer = answer;
    }
}
