
package com.example.shop.domain.qna.entity;

/**
 * @author madey
 * @date 2026-07-27
 * @description QnA 답변 상태 Enum
 */
public enum QnaStatus {
    WAITING("답변 대기"),
    ANSWERED("답변 완료");

    private final String label;

    private QnaStatus(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}
