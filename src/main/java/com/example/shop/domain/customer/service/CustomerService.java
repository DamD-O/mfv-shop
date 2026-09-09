package com.example.shop.domain.customer.service;

import com.example.shop.domain.admin.entity.AuthType;
import com.example.shop.domain.customer.dto.CustomerSignupRequest;
import com.example.shop.domain.customer.dto.WithdrawnRequest;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description 고객 서비스 구현
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public Customer signup(CustomerSignupRequest request)
    {
        //중복 확인 - 아이디, 전화번호, 이메일
        //todo: 사용자 화면에 오류 메세지 출력 구현
        String userID = request.getCustomerId();
        if (customerRepository.existsById(userID))
        {
            log.warn("회원가입 실패 - 이미 존재하는 아이디: {}", userID);
            throw new RuntimeException(userID + "는 이미 존재하는 아이디 입니다.");
        }

        String userEmail = request.getEmail();
        if (customerRepository.existsByEmail(userEmail))
        {
            log.warn("회원가입 실패 - 이미 사용중인 이메일 : {}", userEmail);
            throw new RuntimeException(userEmail + "는 이미 사용중인 이메일 입니다.");
        }

        String userPhone = request.getPhone();
        if (customerRepository.existsByPhone(userPhone))
        {
            log.warn("회원가입 실패 - 이미 사용중인 핸드폰 번호 : {}", userPhone);
            throw new RuntimeException(userPhone + "는 이미 사용중인 핸드폰 번호 입니다.");
        }

        //비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //customer에 저장
        Customer customer = new Customer(request.getCustomerId(), encodedPassword, request.getName(), request.getPhone(), request.getEmail(),
                                         request.getBirthDate(), AuthType.USER);

        //DB에 저장
        customerRepository.save(customer);

        return customer;
    }

    //사용자 탈퇴 : 고객 테이블 탈퇴여부, 탈퇴일시 입력
    @Transactional
    public void withdrawn(WithdrawnRequest request, HttpSession session)
    {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findByCustomerIdAndWithdrawnFalse(customerId).orElseThrow(() -> {
            log.warn("탈퇴실패 - 존재하지 않는 사용자 customerId: {}", customerId);
            return new RuntimeException("탈퇴실패하였습니다. 사유 : 존재하지 않는 사용자 입니다.");
        });

        if (passwordEncoder.matches(request.getPassword(), customer.getPassword())) customer.setWithdrawn();
        else throw new RuntimeException("비밀번호가 일치 하지 않습니다.");

        session.invalidate();
    }
}
