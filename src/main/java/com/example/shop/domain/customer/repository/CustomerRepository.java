package com.example.shop.domain.customer.repository;

import com.example.shop.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Customer> findByCustomerIdAndWithdrawnFalse(String customerId);

    List<Customer> findByCustomerId(String customerId);

    Optional<Customer> findOneByCustomerId(String customerId);

}
