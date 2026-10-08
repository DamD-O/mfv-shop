package com.example.shop.domain.admin.repository;

import com.example.shop.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface AdminRepository extends JpaRepository<Admin, String> {

    Optional<Admin> findOneByAdminId(String adminId);
}
