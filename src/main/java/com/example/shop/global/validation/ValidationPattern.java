package com.example.shop.global.validation;

/**
 * @author madey
 * @DATE 2026-11-09
 * @description
 */


public class ValidationPattern {
    public static final String PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])\\S{8,}$";
    public static final String PASSWORD_MESSAGE = "비밀번호는 영문, 숫자, 특수문자(!,@,#,$,%,^,&,*) 포함 8자 이상이며 공백은 사용할 수 없습니다.";
    public static final String PHONE = "^01[016789]-\\d{3,4}-\\d{4}$";
    public static final String PHONE_MESSAGE = "올바른 휴대폰 번호 형식이 아닙니다. (예: 010-1234-5678)";

    private ValidationPattern()
    {

    }
}
