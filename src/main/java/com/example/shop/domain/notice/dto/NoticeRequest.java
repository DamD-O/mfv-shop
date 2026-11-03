package com.example.shop.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author madey
 * @DATE 2026-11-03
 * @description 공지사항 등록 dto
 */

@Getter
@NoArgsConstructor
public class NoticeRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    @Length(max = 100, message = "100자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "공지 내용을 입력해주세요.")
    @Length(max = 10000, message = "10000자 이내로 입력해주세요.")
    private String content;

    public NoticeRequest(String title, String content)
    {
        this.title = title;
        this.content = content;
    }
}
