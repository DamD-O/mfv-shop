package com.example.shop.domain.address.repository;

import com.example.shop.domain.address.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findByCustomer_CustomerId(String customerId);
    List<DeliveryAddress> findByCustomer_CustomerIdAndIsDefaultTrue(String customerId);
}
