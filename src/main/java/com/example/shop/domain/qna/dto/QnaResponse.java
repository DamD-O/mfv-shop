package com.example.shop.domain.qna.dto;

import com.example.shop.domain.product.entity.ProductImage;
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
    private String productName;
    private String productImage;
    private String customerId;
    private String title;
    private String content;
    private String adminAnswer;
    private QnaStatus status;
    private String statusLabel;
    private LocalDateTime createdAt;

    public QnaResponse(Qna qna)
    {
        this.qnaId = qna.getId();
        this.productId = qna.getProduct().getId();
        this.productName = qna.getProduct().getName();
        this.productImage = qna.getProduct().getProductImages().stream().findFirst().map(ProductImage::getImagePath).orElse(null);
        this.customerId = qna.getCustomer().getCustomerId();
        this.title = qna.getTitle();
        this.content = qna.getContent();
        this.adminAnswer = qna.getAdminAnswer();
        this.status = qna.getStatus();
        this.statusLabel = qna.getStatus().getLabel();
        this.createdAt = qna.getCreatedAt();
    }
}
