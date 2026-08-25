package com.example.shop.global.entity;

import lombok.Getter;

/**
 * @author madey
 * @DATE 2026-08-25
 * @description
 */

@Getter
public enum ImageCategory {
    PRODUCT("product"),
    REVIEW("review");

    private final String folderName;

    ImageCategory(String folderName)
    {
        this.folderName = folderName;
    }
}
