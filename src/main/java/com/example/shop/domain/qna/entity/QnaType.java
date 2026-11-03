package com.example.shop.domain.qna.entity;

/**
 * @author madey
 * @DATE 2026-11-03
 * @description 문의사항 종류
 */


public enum QnaType {
    PRODUCT("상품 문의"),
    PERSONAL("1:1 문의");

    private final String label;

    QnaType(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}