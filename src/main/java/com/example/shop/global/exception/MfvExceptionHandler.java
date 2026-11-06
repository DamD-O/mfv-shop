package com.example.shop.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.Objects;

/**
 * @author madey
 * @DATE 2026-09-14
 * @description 공통 예외처리 핸들러
 */

@Slf4j
@RestControllerAdvice //Controller에서 발생하는 예외 캐치
public class MfvExceptionHandler {
    /**
     *
     * @param ex
     * @return DTO 검증
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        String message = ex.getBindingResult().getFieldErrors().stream().findFirst().map(e -> e.getDefaultMessage()).orElse("입력 값이 올바르지 않습니다.");

        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

    /**
     *
     * @param ex
     * @return 직접 던진 예외
     */
    @ExceptionHandler(MfvException.class)
    public ResponseEntity<?> handleMfvException(MfvException ex)
    {
        return ResponseEntity.badRequest().body(Map.of("message", Objects.requireNonNullElse(ex.getMessage(), "요청을 처리할 수 없습니다.")));
    }

    /**
     *
     * @param ex
     * @return 해당 핸드러에 정의되지 않은 나머지 예외(서버 버그)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex)
    {
        log.error("서버 오류", ex);
        return ResponseEntity.internalServerError().body(Map.of("message", "일시적인 오류가 발생했습니다. 잠시후 다시 시도해주세요."));
    }

    /**
     *
     * @param ex
     * @return URL 값의 타입이 다름
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex)
    {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getName() + " 값이 올바르지 않습니다."));
    }

    /**
     *
     * @param ex
     * @return 필수 URL 파라미터 누락
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex)
    {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getParameterName() + " 값이 필요합니다."));
    }

    /**
     *
     * @param ex
     * @return 요청 본문 JSON 깨짐
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
    {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("message", "요청 형식이 올바르지 않습니다."));
    }
}
