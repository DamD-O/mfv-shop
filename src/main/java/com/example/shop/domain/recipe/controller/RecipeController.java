package com.example.shop.domain.recipe.controller;

import com.example.shop.domain.recipe.dto.RecipeRequest;
import com.example.shop.domain.recipe.dto.RecipeResponse;
import com.example.shop.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author madey
 * @DATE 2026-09-09
 * @description ai 레시피 추천 컨트롤러
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/recipe/recommend")
    public ResponseEntity<RecipeResponse> questionToAi(@RequestBody RecipeRequest request)
    {
        return ResponseEntity.ok(recipeService.questionToAi(request));
    }
}
