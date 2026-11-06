package com.example.shop.global.exception;

/**
 * @author madey
 * @DATE 2026-11-06
 * @description 의도한 업무 예외 처리 (400 응답)
 */


public class MfvException extends RuntimeException {
    public MfvException(String message)
    {
        super(message);
    }
}
