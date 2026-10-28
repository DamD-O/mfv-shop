package com.example.shop.domain.customer.service;

import com.example.shop.domain.admin.entity.AuthType;
import com.example.shop.domain.customer.dto.CustomerResponse;
import com.example.shop.domain.customer.dto.CustomerSignupRequest;
import com.example.shop.domain.customer.dto.CustomerUpdateRequest;
import com.example.shop.domain.customer.dto.WithdrawnRequest;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.order.entity.OrderStatus;
import com.example.shop.domain.order.repository.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final OrdersRepository ordersRepository;

    //회원가입
    @Transactional
    public Customer signup(CustomerSignupRequest request)
    {
        //중복 확인 - 아이디, 전화번호, 이메일
        String userID = request.getCustomerId();
        if (customerRepository.existsById(userID))
        {
            log.warn("회원가입 실패 - 이미 존재하는 아이디: {}", userID);
            throw new RuntimeException(userID + "는 이미 존재하는 아이디 입니다.");
        }

        //비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        String userPhone = request.getPhone().trim();
        if (customerRepository.existsByPhone(userPhone))
        {
            log.warn("회원가입 실패 - 이미 사용중인 핸드폰 번호 : {}", userPhone);
            throw new RuntimeException(userPhone + "는 이미 사용중인 핸드폰 번호 입니다.");
        }

        String userEmail = request.getEmail().trim();
        if (customerRepository.existsByEmail(userEmail))
        {
            log.warn("회원가입 실패 - 이미 사용중인 이메일 : {}", userEmail);
            throw new RuntimeException(userEmail + "는 이미 사용중인 이메일 입니다.");
        }

        //customer에 저장
        Customer customer = new Customer(userID, encodedPassword, request.getName().trim(), userPhone, userEmail, request.getBirthDate(),
                                         AuthType.USER);

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

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword()))
        {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        if (ordersRepository.existsByCustomer_CustomerIdAndOrderStatusIn(customerId, OrderStatus.notAvailWithdrawnStatus()))
            throw new RuntimeException("진행 중인 주문이 있어 탈퇴할 수 없습니다. 배송 완료 또는 주문 취소 후 다시 시도해주세요.");

        customer.setWithdrawn();

        session.invalidate();
    }

    public String getCustomerName(String customerId)
    {
        return customerRepository.findOneByCustomerId(customerId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다.")).getName();
    }

    public CustomerResponse getCustomerInfo(String customerId)
    {
        Customer customer = customerRepository.findByCustomerIdAndWithdrawnFalse(customerId).orElseThrow(() -> {
            log.warn("사용자 정보 조회 실패, customerId: {}", customerId);
            return new RuntimeException("사용자 정보를 가져오기 실패하였습니다.");
        });

        return new CustomerResponse(customer);
    }

    @Transactional
    public Customer updateCustomerInfo(String customerId, CustomerUpdateRequest request)
    {
        Customer customer = customerRepository.findByCustomerIdAndWithdrawnFalse(customerId).orElseThrow(() -> {
            log.warn("사용자 정보 수정 실패, customerId: {}", customerId);
            return new RuntimeException("사용자 정보 수정 - 존재하지 않는 사용자 입니다.");
        });

        if (!passwordEncoder.matches(request.getOldPassword(), customer.getPassword())) throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");

        if (!customer.getEmail().equals(request.getEmail()))
        {
            if (customerRepository.existsByEmail(request.getEmail()))
            {
                log.warn("회원 정보 수정 실패 - 이미 사용중인 이메일 : {}", request.getEmail());
                throw new RuntimeException(request.getEmail() + "는 이미 사용중인 이메일 입니다.");
            }
        }

        if (!customer.getPhone().equals(request.getPhone()))
        {
            if (customerRepository.existsByPhone(request.getPhone()))
            {
                log.warn("회원 정보 수정 실패 - 이미 사용중인 핸드폰 번호 : {}", request.getPhone());
                throw new RuntimeException(request.getPhone() + "는 이미 사용중인 핸드폰 번호 입니다.");
            }
        }

        customer.updateInfo(request.getPhone(), request.getEmail());

        String newPassword = request.getNewPassword();
        if (newPassword != null)
        {
            customer.changePassword(passwordEncoder.encode(newPassword));
        }

        return customer;
    }
}
