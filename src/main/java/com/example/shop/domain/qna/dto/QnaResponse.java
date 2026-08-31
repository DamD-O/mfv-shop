package com.example.shop.domain.qna.dto;

import com.example.shop.domain.qna.entity.Qna;
import com.example.shop.domain.qna.entity.QnaStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description
 */

@Getter
@NoArgsConstructor
public class QnaResponse {
    private Long qnaId;
    private Long productId;
    private String customerId;
    private String title;
    private String content;
    private String adminAnswer;
    private QnaStatus status;
    private LocalDateTime createdAt;

    public QnaResponse(Qna qna)
    {
        this.qnaId = qna.getId();
        this.productId = qna.getProduct().getId();
        this.customerId = qna.getCustomer().getCustomerId();
        this.title = qna.getTitle();
        this.content = qna.getContent();
        this.adminAnswer = qna.getAdminAnswer();
        this.status = qna.getStatus();
        this.createdAt = qna.getCreatedAt();
    }
}
