package com.example.shop.domain.customer.repository;

import com.example.shop.domain.customer.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {}
