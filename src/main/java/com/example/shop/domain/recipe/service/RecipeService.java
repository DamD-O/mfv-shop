package com.example.shop.domain.recipe.service;

import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.entity.ProductStatus;
import com.example.shop.domain.product.repository.ProductRepository;
import com.example.shop.domain.recipe.dto.RecipeRequest;
import com.example.shop.domain.recipe.dto.RecipeResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author madey
 * @DATE 2026-09-09
 * @description 상품 목록 조회 + AI 호출
 */

@Service
public class RecipeService {
    private final ChatClient chatClient;
    private final ProductRepository productRepository;

    public RecipeService(ChatClient.Builder chatClientBuilder, ProductRepository productRepository)
    {
        this.chatClient = chatClientBuilder.build();
        this.productRepository = productRepository;
    }

    public RecipeResponse questionToAi(RecipeRequest request)
    {
        List<Product> products = productRepository.findByStatus(ProductStatus.ON_SALE);
        String product = products.stream().map(Product::getName).collect(Collectors.joining(","));

        String prompt = """
                 당신은 우리가게에서 판매중인 상품 목록(%s)을 기반으로 상품과 레시피를 추천하는 AI입니다.
                 입력된 질문: %s 를 기반으로 우리 가게의 상품과 레시피을 추천해야합니다.
                 답변 형식 :
                 1.필요한 재료 :
                 2.가게 상품 추천 :
                 3.추가 구매 필요 재료 :
                 4.조리 순서
                 5.요리 팁
                * 한국어로 답변해야됩니다.
                * 마크다운 형식 사용하지 말고 일반 텍스트로 보기 쉽게 답변해야 됩니다.
                * 상품 목록에 없는 재료는 반드시 '추가 구매 필요 재료'로만 분류
                """.formatted(product, request.getQuestion());

        return new RecipeResponse(chatClient.prompt().user(prompt).call().content());
    }
}
