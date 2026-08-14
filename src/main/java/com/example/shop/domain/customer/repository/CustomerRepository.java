package com.example.shop.domain.customer.repository;

import com.example.shop.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
