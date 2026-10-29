package com.example.shop.domain.qna.controller;

import com.example.shop.domain.qna.dto.QnaAnswerRequest;
import com.example.shop.domain.qna.dto.QnaCreateRequest;
import com.example.shop.domain.qna.dto.QnaResponse;
import com.example.shop.domain.qna.entity.QnaStatus;
import com.example.shop.domain.qna.service.QnaService;
import com.example.shop.global.dto.PageResponse;
import com.example.shop.global.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public List<QnaResponse> getQnaByProduct(@PathVariable Long productId, Authentication authentication)
    {
        return qnaService.getQnaListByProduct(productId, AuthUtil.getCustomerId(authentication), AuthUtil.getRole(authentication));
    }

    @Operation(summary = "문의 상세조회")
    @GetMapping("/qna/{qnaId}")
    public QnaResponse getQnaById(@PathVariable Long qnaId, Authentication authentication)
    {
        return qnaService.getQnaByQnaId(qnaId, AuthUtil.getCustomerId(authentication), AuthUtil.getRole(authentication));
    }

    @Operation(summary = "문의 답변")
    @PatchMapping("/admin/qna/{qnaId}/answer")
    public QnaResponse answerQna(@PathVariable Long qnaId, @Valid @RequestBody QnaAnswerRequest request)
    {
        return new QnaResponse(qnaService.postQnaAdminAnswer(qnaId, request));
    }

    @Operation(summary = "문의 삭제")
    @DeleteMapping("/customers/qna/{qnaId}")
    public void deleteQna(@PathVariable Long qnaId, Authentication authentication)
    {
        qnaService.deleteQnaByQnaId(qnaId, authentication.getName());
    }

    @Operation(summary = "관리자용 문의 목록 조회")
    @GetMapping("/admin/qna")
    public PageResponse<QnaResponse> getAllQna(@RequestParam(required = false) QnaStatus status, @PageableDefault(size = 10) Pageable pageable)
    {
        return qnaService.getAllQna(status, pageable);
    }

}
