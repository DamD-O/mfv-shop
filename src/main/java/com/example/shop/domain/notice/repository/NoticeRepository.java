package com.example.shop.domain.notice.repository;

import com.example.shop.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author madey
 * @DATE 2026-11-03
 * @description
 */

public interface NoticeRepository extends JpaRepository<Notice, Long> {}