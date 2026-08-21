package com.example.shop.domain.customer.service;

import com.example.shop.domain.admin.entity.AuthType;
import com.example.shop.domain.customer.dto.CustomerSignupRequest;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description 고객 서비스 구현
 */

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Customer signup(CustomerSignupRequest request)
    {
        //중복 확인 - 아이디, 전화번호, 이메일
        //todo: 사용자 화면에 오류 메세지 출력 구현
        if (customerRepository.existsById(request.getCustomerId()))
        {
            throw new RuntimeException(request.getCustomerId() + "는 이미 존재하는 아이디 입니다.");
        }

        if (customerRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException(request.getEmail() + "는 이미 사용중인 이메일 입니다.");
        }

        if (customerRepository.existsByPhone(request.getPhone()))
        {
            throw new RuntimeException(request.getPhone() + "는 이미 사용중인 전화번호 입니다.");
        }

        //비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //customer에 저장
        Customer customer = new Customer(request.getCustomerId(), encodedPassword, request.getName(), request.getPhone(),
                                             request.getEmail(),
                                             request.getBirthDate(), AuthType.USER);

        //DB에 저장
        customerRepository.save(customer);

        return customer;
    }
}
