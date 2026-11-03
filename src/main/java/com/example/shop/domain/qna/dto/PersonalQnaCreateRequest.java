package com.example.shop.domain.qna.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author madey
 * @DATE 2026-11-03
 * @description 1:1 문의 내용 요청 DTO
 */

@Getter
@NoArgsConstructor
public class PersonalQnaCreateRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Length(max = 100, message = "100자 이내로 작성해주세요.")
    private String title;

    @NotBlank(message = "문의 내용을 입력해주세요.")
    @Length(max = 1000, message = "1000자 이내로 작성해주세요.")
    private String content;

    public PersonalQnaCreateRequest(String title, String content)
    {
        this.title = title;
        this.content = content;
    }
}