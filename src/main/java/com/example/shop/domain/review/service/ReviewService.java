package com.example.shop.domain.review.service;

import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.order.entity.OrderDetail;
import com.example.shop.domain.order.entity.OrderStatus;
import com.example.shop.domain.order.repository.OrderDetailRepository;
import com.example.shop.domain.product.repository.ProductRepository;
import com.example.shop.domain.review.dto.ReviewRequest;
import com.example.shop.domain.review.entity.Review;
import com.example.shop.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-09-08
 * @description
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    //리뷰 작성
    @Transactional
    public Review writeReview(String customerId, ReviewRequest request)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.warn("리뷰 등록 - 존재하지 않는 사용자 customerId : {}", customerId);
            return new RuntimeException("존재하지 않는 사용자 입니다.");
        });

        OrderDetail orderDetail = orderDetailRepository.findById(request.getOrderDetailId()).orElseThrow(() -> {
            log.warn("리뷰 등록 - 존재하지 않는 상세주문 orderDetailId : {}", request.getOrderDetailId());
            return new RuntimeException("존재하지 않는 상세 주문 입니다.");
        });

        if (!orderDetail.getOrders().getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인이 주문한 상품만 리뷰 작성 가능합니다.");

        if (!orderDetail.getOrders().getOrderStatus().equals(OrderStatus.DELIVERED)) throw new RuntimeException("배송이 완료된 상품에 한해서 리뷰 작성 가능합니다.");

        Review review = new Review(customer, orderDetail, request.getRating(), request.getContent());
        reviewRepository.save(review);

        return review;
    }

    //상품별 리뷰 목록 조회 - 상품 상세페이지
    public List<Review> getReviewsByProductId(Long productId)
    {
        productRepository.findById(productId).orElseThrow(() -> {
            log.warn("상품별 리뷰 목록 조회 - 존재하지 않는 상품 입니다. product id : {}", productId);
            return new RuntimeException("상품 별 리뷰 목록 조회 - 존재하지 않는 상품입니다.");
        });

        return reviewRepository.findByOrderDetail_Product_Id(productId);
    }

    //리뷰 수정
    @Transactional
    public Review updateReview(String customerId, Long reviewId, ReviewRequest request)
    {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            log.warn("리뷰 수정 - 존재하지 않는 리뷰 reviewId : {}", reviewId);
            return new RuntimeException("존재하지 않는 리뷰 입니다.");
        });

        if (!review.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인이 작성한 리뷰만 수정 가능합니다.");

        review.updateContent(request.getRating(), request.getContent());

        return review;
    }

    //리뷰 삭제
    @Transactional
    public void deleteReview(String customerId, Long reviewId)
    {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            log.warn("리뷰삭제 - 존재하지 않는 리뷰, reviewId : {}", reviewId);
            return new RuntimeException("존재하지 않는 리뷰입니다.");
        });

        if (!review.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인이 작성한 리뷰만 삭제 가능합니다.");

        reviewRepository.delete(review);
    }
}
