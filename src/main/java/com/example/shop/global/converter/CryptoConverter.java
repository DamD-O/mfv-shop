/**
 * @author yedam
 * @date 2026-07-27
 * @description 개인정보 필드 AES-256 암호화/복호화 컨버터
 */
package com.example.shop.global.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@Converter
public class CryptoConverter implements AttributeConverter<String, String> {
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    //암호화 키 -> application.yaml.encrypt
    @Value("${encrypt.key}") //todo : 암호화키 확인
    private String secretKey;

    //JAVA -> DB, 암호화(삽입 및 수정)
    //Hibernate가 INSERT/UPDATE 직전에 자동으로 메서드 호출
    @Override
    public String convertToDatabaseColumn(String attribute)
    {
        if (attribute == null)
        {
            return null;
        }
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM); //암호화 알고리즘 준비
            cipher.init(Cipher.ENCRYPT_MODE, keySpec()); //암호화 모드 작동 + 비밀키 세팅
            byte[] encrypted = cipher.doFinal(attribute.getBytes()); //실제 암호화 실행
            return Base64.getEncoder().encodeToString(encrypted); //암호화된 바이트를 DB에 저장 가능한 문자열로 변환
        }
        catch (Exception e)
        {
            throw new IllegalStateException("암호화 처리 중 오류가 발생했습니다.", e);
        }
    }

    //DB -> JAVA, 복호화(조회)
    //.findById() 실행해서 Entity를 조립할 때, Hibernate가 자동으로 메서드 호출
    @Override
    public String convertToEntityAttribute(String dbData)
    {
        if (dbData == null)
        {
            return null;
        }
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM); //알고리즘 세팅
            cipher.init(Cipher.DECRYPT_MODE, keySpec()); //복호화 모드 동작 + 비밀키 세팅
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(dbData)); //실제 복호화 실행
            return new String(decrypted);
        }
        catch (Exception e)
        {
            throw new IllegalStateException("복호화 처리 중 오류가 발생했습니다.", e);
        }
    }

    private SecretKeySpec keySpec()
    {
        return new SecretKeySpec(secretKey.getBytes(), "AES");
    }
}
