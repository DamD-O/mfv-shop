package com.example.shop.domain.qna.controller;

import com.example.shop.domain.qna.dto.QnaAnswerRequest;
import com.example.shop.domain.qna.dto.QnaCreateRequest;
import com.example.shop.domain.qna.dto.QnaResponse;
import com.example.shop.domain.qna.service.QnaService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/customers/qna")
    public QnaResponse createQna(@Valid @RequestBody QnaCreateRequest request, Authentication authentication)
    {
        return new QnaResponse(qnaService.createQna(authentication.getName(), request));
    }

    @GetMapping("/customers/qna")
    public List<QnaResponse> getQnaListByCustomer(Authentication authentication)
    {
        return qnaService.getQnaListByCustomer(authentication.getName()).stream().map(QnaResponse::new).toList();
    }

    @GetMapping("/products/{productId}/qna")
    public List<QnaResponse> getQnaByProduct(@PathVariable("productId") Long productId)
    {
        return qnaService.getQnaListByProduct(productId).stream().map(QnaResponse::new).toList();
    }

    @GetMapping("/qna/{qnaId}")
    public QnaResponse getQnaById(@PathVariable Long qnaId)
    {
        return new QnaResponse(qnaService.getQnaByQnaId(qnaId));
    }

    @PatchMapping("/admin/qna/{qnaId}/answer")
    public QnaResponse answerQna(@PathVariable Long qnaId, @Valid @RequestBody QnaAnswerRequest request)
    {
        return new QnaResponse(qnaService.postQnaAdminAnswer(qnaId, request));
    }

    @DeleteMapping("/customers/qna/{qnaId}")
    public void deleteQna(@PathVariable Long qnaId,  Authentication authentication)
    {
        qnaService.deleteQnaByQnaId(qnaId, authentication.getName());
    }
}
