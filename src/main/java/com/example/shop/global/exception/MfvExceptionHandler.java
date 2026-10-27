package com.example.shop.global.exception;

import org.springframework.http.ResponseEntity;
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

@RestControllerAdvice //Controller에서 발생하는 예외 캐치
public class MfvExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        String message = ex.getBindingResult().getFieldErrors().stream().findFirst().map(e -> e.getDefaultMessage()).orElse("입력 값이 올바르지 않습니다.");

        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e)
    {
        return ResponseEntity.badRequest().body(Map.of("message", Objects.requireNonNullElse(e.getMessage(), "요청을 처리할 수 없습니다.")));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex)
    {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getName() + " 값이 올바르지 않습니다."));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex)
    {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getParameterName() + " 값이 필요합니다."));
    }
}
