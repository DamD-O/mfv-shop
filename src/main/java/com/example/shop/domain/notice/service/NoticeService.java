package com.example.shop.domain.notice.service;

import com.example.shop.domain.notice.dto.NoticeRequest;
import com.example.shop.domain.notice.dto.NoticeResponse;
import com.example.shop.domain.notice.entity.Notice;
import com.example.shop.domain.notice.repository.NoticeRepository;
import com.example.shop.global.dto.PageResponse;
import com.example.shop.global.exception.MfvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author madey
 * @DATE 2026-11-03
 * @description 공지사항 service
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public NoticeResponse saveNotice(NoticeRequest request)
    {
        Notice notice = new Notice(request.getTitle(), request.getContent());
        return new NoticeResponse(noticeRepository.save(notice));
    }

    @Transactional(readOnly = true)
    public PageResponse<NoticeResponse> getNotices(Pageable pageable)
    {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        return new PageResponse<>(noticeRepository.findAll(sortedPageable).map(NoticeResponse::new));
    }

    @Transactional(readOnly = true)
    public NoticeResponse getNotice(Long noticeId)
    {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.warn("공지사항 상세 조회 - 존재하지 않는 공지사항 입니다. noticeId: {}", noticeId);
            return new MfvException("공지사항 상세조회 실패, 존재하지 않는 공지사항입니다.");
        });

        return new NoticeResponse(notice);
    }

    @Transactional
    public NoticeResponse updateNotice(Long noticeId, NoticeRequest request)
    {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.warn("공지사항 수정 - 존재하지 않는 공지사항 입니다. noticeId: {}", noticeId);
            return new MfvException("공지사항 수정 실패, 존재하지 않는 공지사항입니다.");
        });

        notice.updateNotice(request.getTitle(), request.getContent());
        return new NoticeResponse(notice);
    }
}