package com.example.shop.domain.qna.controller;

import com.example.shop.domain.qna.dto.QnaAnswerRequest;
import com.example.shop.domain.qna.dto.QnaCreateRequest;
import com.example.shop.domain.qna.dto.QnaResponse;
import com.example.shop.domain.qna.service.QnaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description
 */

@Tag(name = "QnA", description = "QnA 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QnaController {
    private final QnaService qnaService;

    @Operation(summary = "문의 등록")
    @PostMapping("/customers/qna")
    public QnaResponse createQna(@Valid @RequestBody QnaCreateRequest request, Authentication authentication)
    {
        return new QnaResponse(qnaService.createQna(authentication.getName(), request));
    }

    @Operation(summary = "고객별 문의 조회")
    @GetMapping("/customers/qna")
    public List<QnaResponse> getQnaListByCustomer(Authentication authentication)
    {
        return qnaService.getQnaListByCustomer(authentication.getName()).stream().map(QnaResponse::new).toList();
    }

    @Operation(summary = "상품별 문의 조회")
    @GetMapping("/products/{productId}/qna")
    public List<QnaResponse> getQnaByProduct(@PathVariable("productId") Long productId)
    {
        return qnaService.getQnaListByProduct(productId).stream().map(QnaResponse::new).toList();
    }

    @Operation(summary = "문의 상세조회")
    @GetMapping("/qna/{qnaId}")
    public QnaResponse getQnaById(@PathVariable Long qnaId)
    {
        return new QnaResponse(qnaService.getQnaByQnaId(qnaId));
    }

    @Operation(summary = "문의 답변")
    @PatchMapping("/admin/qna/{qnaId}/answer")
    public QnaResponse answerQna(@PathVariable Long qnaId, @Valid @RequestBody QnaAnswerRequest request)
    {
        return new QnaResponse(qnaService.postQnaAdminAnswer(qnaId, request));
    }

    @Operation(summary = "문의 삭제")
    @DeleteMapping("/customers/qna/{qnaId}")
    public void deleteQna(@PathVariable Long qnaId,  Authentication authentication)
    {
        qnaService.deleteQnaByQnaId(qnaId, authentication.getName());
    }
}
