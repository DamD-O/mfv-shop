package com.example.shop.domain.qna.service;

import com.example.shop.domain.customer.entity.Customer;
import com.example.shop.domain.customer.repository.CustomerRepository;
import com.example.shop.domain.product.entity.Product;
import com.example.shop.domain.product.repository.ProductRepository;
import com.example.shop.domain.qna.dto.QnaAnswerRequest;
import com.example.shop.domain.qna.dto.QnaCreateRequest;
import com.example.shop.domain.qna.dto.QnaResponse;
import com.example.shop.domain.qna.entity.Qna;
import com.example.shop.domain.qna.entity.QnaStatus;
import com.example.shop.domain.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

        Qna qna = new Qna(product, customer, request.getTitle(), request.getContent(), request.isSecret());

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
    @Transactional(readOnly = true)
    public List<QnaResponse> getQnaListByProduct(Long productId, String customerId, String role)
    {
        productRepository.findById(productId).orElseThrow(() -> {
            log.warn("상품별 문의 목록 조회 - 존재하지 않는 상품 입니다. product id : {}", productId);
            return new RuntimeException("상품 별 문의 목록 조회 - 존재하지 않는 상품입니다.");
        });

        List<Qna> qnaList = qnaRepository.findByProduct_IdOrderByCreatedAtDesc(productId);

        return qnaList.stream().map(qna -> {
            QnaResponse qnaResponse = new QnaResponse(qna);
            boolean canView = isViewable(qna, customerId, role);
            qnaResponse.maskQna(canView);
            return qnaResponse;
        }).toList();
    }

    //목록 상세 조회
    @Transactional(readOnly = true)
    public QnaResponse getQnaByQnaId(Long qnaId, String customerId, String role)
    {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(() -> {
            log.warn("문의 상세조회 실패 - 존재하지 않는 qnaId: {}", qnaId);
            return new RuntimeException("존재하지 않는 문의 사항입니다.");
        });

        boolean canView = isViewable(qna, customerId, role);
        QnaResponse qnaResponse = new QnaResponse(qna);
        qnaResponse.maskQna(canView);

        return qnaResponse;
    }

    //사용자 상품별 문의사항 목록
    @Transactional(readOnly = true)
    public List<Qna> getQnaListByCustomer(String customerId)
    {
        return qnaRepository.findByCustomer_CustomerIdOrderByCreatedAtDesc(customerId);
    }

    //문의 삭제 - 답변이 안된 경우만
    @Transactional
    public void deleteQnaByQnaId(Long qnaId, String customerId)
    {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(() -> {
            log.warn("QnA 삭제 실패 - 존재하지 않는 문의 입니다. qnaId: {}", qnaId);
            return new RuntimeException("QnA 삭제 실패 - 존재하지 않는 문의입니다.");
        });

        if (!qna.getCustomer().getCustomerId().equals(customerId)) throw new RuntimeException("본인의 문의사항만 삭제 할 수 있습니다.");

        if (qna.getStatus().equals(QnaStatus.ANSWERED)) throw new RuntimeException("답변이 완료된 문의는 삭제 할 수 없습니다.");

        qnaRepository.delete(qna);
    }

    /**
     * 관리자용 QnA 목록 조회
     */
    @Transactional(readOnly = true)
    public List<QnaResponse> getAllQna(QnaStatus status)
    {
        List<Qna> qna;

        if (status != null) qna = qnaRepository.findByStatusOrderByCreatedAtDesc(status);
        else qna = qnaRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return qna.stream().map(QnaResponse::new).toList();
    }

    private boolean isViewable(Qna qna, String customerId, String role)
    {
        return (qna.getCustomer().getCustomerId().equals(customerId) || "ROLE_ADMIN".equals(role));
    }
}
