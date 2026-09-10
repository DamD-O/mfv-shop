package com.example.shop.domain.address.controller;

import com.example.shop.domain.address.dto.DeliveryAddressRequest;
import com.example.shop.domain.address.dto.DeliveryAddressResponse;
import com.example.shop.domain.address.service.DeliveryAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "배송지", description = "배송지 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/address")
public class DeliveryAddressController {
    private final DeliveryAddressService deliveryAddressService;

    @Operation(summary = "배송지 등록")
    @PostMapping()
    public DeliveryAddressResponse createAddress(@Valid @RequestBody DeliveryAddressRequest request, Authentication authentication)
    {
        return new DeliveryAddressResponse(deliveryAddressService.createDeliveryAddress(authentication.getName(), request));
    }

    @Operation(summary = "배송지 수정")
    @PutMapping("/{deliveryId}")
    public DeliveryAddressResponse updateAddress(@PathVariable Long deliveryId, @Valid @RequestBody DeliveryAddressRequest request, Authentication authentication)
    {
        return new DeliveryAddressResponse(deliveryAddressService.updateDeliveryAddress(authentication.getName(), deliveryId, request));
    }

    @Operation(summary = "배송지 목록 조회")
    @GetMapping
    public List<DeliveryAddressResponse> getList(Authentication authentication)
    {
        return deliveryAddressService.getDeliveryAddressList(authentication.getName()).stream().map(DeliveryAddressResponse::new).toList();
    }

    @Operation(summary = "배송지 삭제")
    @DeleteMapping("/{deliveryId}")
    public void deleteAddress(@PathVariable Long deliveryId, Authentication authentication)
    {
        deliveryAddressService.deleteDeliveryAddress(authentication.getName(), deliveryId);
    }

    @Operation(summary = "기본배송지 설정")
    @PatchMapping("/{deliveryId}/default")
    public void setDefault(@PathVariable Long deliveryId, Authentication authentication)
    {
        deliveryAddressService.setIsDefault(authentication.getName(), deliveryId);
    }
}
