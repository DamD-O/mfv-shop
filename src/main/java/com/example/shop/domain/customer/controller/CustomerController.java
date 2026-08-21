package com.example.shop.domain.customer.controller;

import com.example.shop.domain.customer.dto.CustomerSignupRequest;
import com.example.shop.domain.customer.dto.CustomerSignupResponse;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author madey
 * @DATE 2026-07-29
 * @description
 */

@Slf4j
@RestController
@RequestMapping("/api/customers/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    //회원가입
    @PostMapping("/signup")
    public CustomerSignupResponse signup(@Valid @RequestBody CustomerSignupRequest request)
    {
        Customer customer = customerService.signup(request);
        return new CustomerSignupResponse(customer);
    }
}
