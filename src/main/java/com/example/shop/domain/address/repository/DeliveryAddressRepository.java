package com.example.shop.domain.address.repository;

import com.example.shop.domain.address.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findByCustomer_CustomerIdAndDeletedAtIsNullOrderByIsDefaultDesc(String customerId);

    List<DeliveryAddress> findByCustomer_CustomerIdAndIsDefaultTrueAndDeletedAtIsNull(String customerId);

    Optional<DeliveryAddress> findByCustomer_CustomerIdAndDeliveryIdAndDeletedAtIsNull(String customerId, Long DeliveryId);
}
