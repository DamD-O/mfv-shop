package com.example.shop.domain.admin.service;

import com.example.shop.domain.admin.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description 관리자 서비스
 */

@RequiredArgsConstructor //생성자 방식으로 주입
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public String getAdminName(String adminId)
    {
        return adminRepository.findOneByAdminId(adminId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다.")).getName();
    }
}
