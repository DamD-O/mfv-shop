/**
 * @author yedam
 * @date 2026-07-27
 * @description 우편번호/도로명주소 등 순수 주소 정보 Entity
 */
package com.example.shop.domain.address.entity;

import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "address")
public class Address extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "zipcode", nullable = false, length = 10)
    private String zipcode;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @Column(name = "building_no", length = 500)
    private String buildingNo;

    @Column(name = "detail_address", length = 500)
    private String detailAddress;

    public Address(String zipcode, String roadAddress, String buildingNo, String detailAddress)
    {
        this.zipcode = zipcode;
        this.roadAddress = roadAddress;
        this.buildingNo = buildingNo;
        this.detailAddress = detailAddress;
    }
}
