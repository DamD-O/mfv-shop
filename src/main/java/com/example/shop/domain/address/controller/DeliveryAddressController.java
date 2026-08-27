package com.example.shop.domain.address.controller;

import com.example.shop.domain.address.dto.DeliveryAddressRequest;
import com.example.shop.domain.address.dto.DeliveryAddressResponse;
import com.example.shop.domain.address.service.DeliveryAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-27
 * @description 배송지 컨트롤러
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/address")
public class DeliveryAddressController {
    private final DeliveryAddressService deliveryAddressService;

    @PostMapping()
    public DeliveryAddressResponse createAddress(@Valid @RequestBody DeliveryAddressRequest request, Authentication authentication)
    {
        return new DeliveryAddressResponse(deliveryAddressService.createDeliveryAddress(authentication.getName(), request));
    }

    @PutMapping("/{deliveryId}")
    public DeliveryAddressResponse updateAddress(@PathVariable Long deliveryId, @Valid @RequestBody DeliveryAddressRequest request, Authentication authentication)
    {
        return new DeliveryAddressResponse(deliveryAddressService.updateDeliveryAddress(authentication.getName(), deliveryId, request));
    }

    @GetMapping
    public List<DeliveryAddressResponse> getList(Authentication authentication)
    {
        return deliveryAddressService.getDeliveryAddressList(authentication.getName()).stream().map(DeliveryAddressResponse::new).toList();
    }

    @DeleteMapping("/{deliveryId}")
    public void deleteAddress(@PathVariable Long deliveryId, Authentication authentication)
    {
        deliveryAddressService.deleteDeliveryAddress(authentication.getName(), deliveryId);
    }

    @PatchMapping("/{deliveryId}/default")
    public void setDefault(@PathVariable Long deliveryId, Authentication authentication)
    {
        deliveryAddressService.setIsDefault(authentication.getName(), deliveryId);
    }
}
