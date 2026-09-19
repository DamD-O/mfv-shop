package com.example.shop.domain.customer.controller;

import com.example.shop.domain.customer.dto.CustomerSignupRequest;
import com.example.shop.domain.customer.dto.CustomerSignupResponse;
import com.example.shop.domain.customer.dto.WithdrawnRequest;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author madey
 * @DATE 2026-07-29
 * @description
 */

@Tag(name = "회원", description = "회원 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public CustomerSignupResponse signup(@Valid @RequestBody CustomerSignupRequest request)
    {
        Customer customer = customerService.signup(request);
        return new CustomerSignupResponse(customer);
    }

    @Operation(summary = "회원 탈퇴", description = "탈퇴 여부 및 탈퇴 일시 변경")
    @DeleteMapping("/withdrawn")
    public ResponseEntity<Void> withdrawn(@Valid @RequestBody WithdrawnRequest request, HttpSession session)
    {
        customerService.withdrawn(request, session);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyPage()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String customerId = auth.getName();
        String role = auth.getAuthorities().stream().findFirst().map(a -> a.getAuthority()).orElse("");

        return ResponseEntity.ok(Map.of("customerId", customerId, "role", role, "name", customerService.getCustomerName(customerId)));
    }
}
