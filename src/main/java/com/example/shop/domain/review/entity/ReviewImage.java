package com.example.shop.domain.review.entity;

import com.example.shop.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author yedam
 * @DATE 2026-07-28
 * @description 리뷰 이미지 Entity
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_image")
public class ReviewImage extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    public ReviewImage(Review review, String imagePath)
    {
        this.review = review;
        this.imagePath = imagePath;
    }

    @Override
    public String toString()
    {
        return "ReviewImage{" + "id=" + id + ", review=" + review + ", imagePath='" + imagePath + '\'' + '}';
    }

    public void changeImagePath(String imagePath)
    {
        //이미지 수정
        this.imagePath = imagePath;
    }
}
