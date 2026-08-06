package com.example.shop.domain.admin.repository;

import com.example.shop.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yedam
 * @DATE 2026-07-28
 * @description
 */
public interface AdminRepository extends JpaRepository<Admin, String> {

}
