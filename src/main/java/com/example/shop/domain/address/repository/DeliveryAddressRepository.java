package com.example.shop.domain.address.repository;

import com.example.shop.domain.address.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description
 */

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

}
