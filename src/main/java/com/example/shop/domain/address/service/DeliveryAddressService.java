package com.example.shop.domain.address.service;

import com.example.shop.domain.address.dto.DeliveryAddressRequest;
import com.example.shop.domain.address.dto.DeliveryAddressResponse;
import com.example.shop.domain.address.entity.DeliveryAddress;
import com.example.shop.domain.address.repository.DeliveryAddressRepository;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-27
 * @description 베송지 서비스
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public DeliveryAddress createDeliveryAddress(String customerId, DeliveryAddressRequest request)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.warn("존재하지 않는 사용자 customerId : {}", customerId);
            return new RuntimeException("존재하지 않는 사용자 입니다.");
        });

        DeliveryAddress deliveryAddress = new DeliveryAddress(customer, request.getAddressName(), request.getReceiver(), request.getContact(),
                                                              request.getZipcode(), request.getRoadAddress(), request.getDetailAddress(), false,
                                                              null);

        deliveryAddressRepository.save(deliveryAddress);

        return deliveryAddress;
    }

    @Transactional
    public DeliveryAddress updateDeliveryAddress(String customerId, Long deliveryId, DeliveryAddressRequest request)
    {
        DeliveryAddress address = deliveryAddressRepository.findById(deliveryId).orElseThrow(() -> {
            log.warn("존재하지 않는 배송지 입니다. deliveryId : {}", deliveryId);
            return new RuntimeException("존재하지 않는 배송지 입니다. 다시 확인 부탁드립니다.");
        });

        if (!address.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인의 배송지만 수정 할 수 있습니다.");

        address.update(request.getAddressName(), request.getReceiver(), request.getContact(), request.getZipcode(), request.getRoadAddress(),
                       request.getDetailAddress());

        return address;
    }

    public List<DeliveryAddress> getDeliveryAddressList(String customerId)
    {
        //로그인한 고객의 배송지 목록 조회
        return deliveryAddressRepository.findByCustomer_CustomerIdAndDeletedAtIsNullOrderByIsDefaultDesc(customerId);
    }

    public DeliveryAddressResponse getDeliveryAddress(String customerId, Long deliveryId)
    {
        //배송지 단건 조회
        DeliveryAddress address = deliveryAddressRepository.findByCustomer_CustomerIdAndDeliveryIdAndDeletedAtIsNull(customerId, deliveryId)
                                                           .orElseThrow(() -> new EntityNotFoundException("배송지를 찾을 수 없습니다."));

        return new DeliveryAddressResponse(address);
    }

    @Transactional
    public void deleteDeliveryAddress(String customerId, Long deliveryId)
    {
        DeliveryAddress address = deliveryAddressRepository.findById(deliveryId).orElseThrow(() -> {
            log.warn("존재하지 않는 배송지 입니다. deliveryId : {}", deliveryId);
            return new RuntimeException("존재하지 않는 배송지 입니다. 다시 확인 부탁드립니다.");
        });

        if (!address.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인의 배송지만 삭제 할 수 있습니다.");

        if (address.isDefault()) throw new IllegalStateException("기본 배송지는 삭제 할 수 없습니다.");

        address.setDeletedAt(LocalDateTime.now());
    }

    @Transactional
    public void setIsDefault(String customerId, Long deliveryId)
    {
        DeliveryAddress target = deliveryAddressRepository.findById(deliveryId).orElseThrow(() -> {
            log.warn("존재하지 않는 배송지 - deliveryId: {}", deliveryId);
            return new RuntimeException("존재하지 않는 배송지 입니다.");
        });

        if (!target.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인의 배송지만 기본배송지로 설정할 수 있습니다.");

        //기존 설정된 기본 배송지 false로 변경
        List<DeliveryAddress> defaultAddress = deliveryAddressRepository.findByCustomer_CustomerIdAndIsDefaultTrueAndDeletedAtIsNull(customerId);

        defaultAddress.forEach(address -> address.changeDefault(false));

        //기본 배송지 설정
        target.changeDefault(true);
    }
}
