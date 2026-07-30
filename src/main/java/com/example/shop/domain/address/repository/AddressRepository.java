package com.example.shop.domain.address.repository;

import com.example.shop.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yedam
 * @DATE 2026-07-28
 * @description 주소 리포지터리(테이블 데이터 저장/수정/삭제 등을 도와주는 인터페이스)
 * 테이블에 접근, 데이터 관리하는 메서드 제공(findAll, save 등)
 */

public interface AddressRepository extends JpaRepository<Address, Long>
{

}
