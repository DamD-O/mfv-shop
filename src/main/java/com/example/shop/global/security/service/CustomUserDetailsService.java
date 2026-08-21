package com.example.shop.global.security.service;

import com.example.shop.domain.admin.entity.Admin;
import com.example.shop.domain.admin.repository.AdminRepository;
import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author madey
 * @DATE 2026-08-18
 * @description
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    //사용자 아이디 조회
    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException
    {
        //관리자 아이디가 존재하면 관리자 조회
        Optional<Admin> adminOpt = adminRepository.findById(userID);
        if (adminOpt.isPresent())
        {
            Admin admin = adminOpt.get();
            UserDetails adminUser = User.builder()
                                        .username(admin.getAdminId())
                                        .password(admin.getPassword())
                                        .roles("ADMIN")
                                        .build();
            return adminUser;
        }

        //관리자 아이디가 없으면 사용자 조회, 사용자도 없으면 UsernameNotFound 처리
        Customer customer = customerRepository.findById(userID)
                                              .orElseThrow(() -> new UsernameNotFoundException(userID));
        UserDetails customerUser = User.builder()
                                       .username(customer.getCustomerId())
                                       .password(customer.getPassword())
                                       .roles("USER")
                                       .build();
        return customerUser;
    }
}
