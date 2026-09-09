package com.example.shop.domain.customer.controller;

import com.example.shop.domain.customer.dto.CustomerSignupRequest;
import com.example.shop.domain.customer.dto.CustomerSignupResponse;
import com.example.shop.domain.customer.dto.WithdrawnRequest;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author madey
 * @DATE 2026-07-29
 * @description
 */

@Slf4j
@RestController
@RequestMapping("/api/customers")
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

    //회원탈퇴
    @DeleteMapping("/withdrawn")
    public ResponseEntity<Void> withdrawn(@Valid @RequestBody WithdrawnRequest request, HttpSession session)
    {
        customerService.withdrawn(request, session);
        return ResponseEntity.ok().build();
    }

}
