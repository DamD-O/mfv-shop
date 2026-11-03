package com.example.shop.domain.notice.controller;

import com.example.shop.domain.notice.dto.NoticeRequest;
import com.example.shop.domain.notice.dto.NoticeResponse;
import com.example.shop.domain.notice.service.NoticeService;
import com.example.shop.global.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author madey
 * @DATE 2026-11-03
 * @description
 */
@Tag(name = "공지사항", description = "공지사항 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 등록", description = "관리자 공지사항 등록")
    @PostMapping("/admin/notice")
    public NoticeResponse saveNotice(@RequestBody @Valid NoticeRequest request)
    {
        return noticeService.saveNotice(request);
    }

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록 조회")
    @GetMapping("/notice")
    public PageResponse<NoticeResponse> getNotices(@PageableDefault(size = 10) Pageable pageable)
    {
        return noticeService.getNotices(pageable);
    }

    @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세내용 조회")
    @GetMapping("/notice/{noticeId}")
    public NoticeResponse getNotice(@PathVariable("noticeId") Long noticeId)
    {
        return noticeService.getNotice(noticeId);
    }

    @Operation(summary = "공지사항 수정", description = "관리자 공지사항 수정")
    @PutMapping("/admin/notice/{noticeId}")
    public NoticeResponse updateNotice(@PathVariable Long noticeId, @RequestBody @Valid NoticeRequest request)
    {
        return noticeService.updateNotice(noticeId, request);
    }
}
