package com.example.shop.domain.qna.service;

import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.repository.ProductRepository;
import com.example.shop.domain.qna.dto.QnaAnswerRequest;
import com.example.shop.domain.qna.dto.QnaCreateRequest;
import com.example.shop.domain.qna.entity.Qna;
import com.example.shop.domain.qna.entity.QnaStatus;
import com.example.shop.domain.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author madey
 * @DATE 2026-08-31
 * @description
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    //Qna 등록
    @Transactional
    public Qna createQna(String customerId, QnaCreateRequest request)
    {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.warn("문의 등록 - 존재하지 않는 사용자 customerId : {}", customerId);
            return new RuntimeException("존재하지 않는 사용자 입니다.");
        });

        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> {
            log.warn("문의 등록 - 존재하지 않는 상품 product id : {}", request.getProductId());
            return new RuntimeException("존재하지 않는 상품입니다.");
        });

        Qna qna = new Qna(product, customer, request.getTitle(), request.getContent());

        qnaRepository.save(qna);

        return qna;
    }

    //관리자 답변 등록
    @Transactional
    public Qna postQnaAdminAnswer(Long qnaId, QnaAnswerRequest request)
    {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(() -> {
            log.warn("존재하지 않는 문의 qnaId: {}", qnaId);
            return new RuntimeException("존재 하지 않는 문의 사항입니다.");
        });

        qna.answer(request.getAnswer());

        return qna;
    }

    //상품별 문의 목록 조회
    public List<Qna> getQnaListByProduct(Long productId)
    {
        productRepository.findById(productId).orElseThrow(() -> {
            log.warn("상품별 문의 목록 조회 - 존재하지 않는 상품 입니다. product id : {}", productId);
            return new RuntimeException("상품 별 문의 목록 조회 - 존재하지 않는 상품입니다.");
        });

        return qnaRepository.findByProduct_Id(productId);
    }

    //목록 상세 조회
    public Qna getQnaByQnaId(Long qnaId)
    {
        return qnaRepository.findById(qnaId).orElseThrow(() -> {
            log.warn("문의 상세조회 실패 - 존재하지 않는 qnaId: {}", qnaId);
            return new RuntimeException("존재하지 않는 문의 사항입니다.");
        });
    }

    //사용자 상품별 문의사항 목록
    public List<Qna> getQnaListByCustomer(String customerId)
    {
        return qnaRepository.findByCustomer_CustomerId(customerId);
    }

    //문의 삭제 - 답변이 안된 경우만
    @Transactional
    public void deleteQnaByQnaId(Long qnaId, String customerId)
    {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(() -> {
            log.warn("QnA 삭제 실패 - 존재하지 않는 문의 입니다. qnaId: {}", qnaId);
            return new RuntimeException("QnA 삭제 실패 - 존재하지 않는 문의입니다.");
        });

        if (!qna.getCustomer().getCustomerId().equals(customerId))
            throw new RuntimeException("본인의 문의사항만 삭제 할 수 있습니다.");

        if (qna.getStatus().equals(QnaStatus.ANSWERED))
            throw new RuntimeException("답변이 완료된 문의는 삭제 할 수 없습니다.");

        qnaRepository.delete(qna);
    }
}
