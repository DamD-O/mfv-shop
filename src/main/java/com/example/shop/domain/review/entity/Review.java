package com.example.shop.domain.review.entity;

import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.order.entity.OrderDetail;
import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author madey
 * @DATE 2026-07-28
 * @description 리뷰 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_id", nullable = false, unique = true)
    private OrderDetail orderDetail;

    //평점 1~5점 체크
    @Column(name = "rating", nullable = false, columnDefinition = "TINYINT CHECK (rating BETWEEN 1 AND 5)")
    private Integer rating;

    @Column(name = "content", length = 1000)
    private String content;

    public Review(Customer customer, OrderDetail orderDetail, Integer rating, String content)
    {
        validateRating(rating);
        this.customer = customer;
        this.orderDetail = orderDetail;
        this.rating = rating;
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "Review{" + "id=" + id + ", customer=" + customer + ", orderDetail=" + orderDetail + ", rating=" + rating + ", content='" + content + '\'' + '}';
    }

    public void updateContent(Integer rating, String content)
    {
        //리뷰 수정 메소드
        validateRating(rating);
        this.rating = rating;
        this.content = content;
    }

    private void validateRating(Integer rating)
    {
        //평점 유효성 체크
        if (rating == null || rating < 1 || rating > 5) throw new IllegalArgumentException("평점은 1~5점 사이여야 합니다.");
    }
}

