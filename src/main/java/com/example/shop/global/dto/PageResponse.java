package com.example.shop.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-10-29
 * @description 페이지 처리 응답 DTO
 */

@Getter
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int currentPage;
    private int pageSize;
    private long totalCount;
    private int totalPage;
    private boolean hasNext;
    private boolean hasPrevious;

    public PageResponse(Page<T> page)
    {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.totalCount = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }
}
