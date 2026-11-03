package com.example.shop.domain.notice.dto;

import com.example.shop.domain.notice.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author madey
 * @DATE 2026-11-03
 * @description 공지사항 응답 DTO
 */

@Getter
@NoArgsConstructor
public class NoticeResponse {
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NoticeResponse(Notice notice)
    {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }
}
