package com.io.rol.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

// 게시글 작성 DTO
@Getter @Setter
@NoArgsConstructor
public class BoardDto {

    private List<MultipartFile> file = new ArrayList<>(); // 파일 목록

    @NotBlank(message = "제목을 작성해주세요.")
    @Length(max = 50, message = "제목은 50자 이상일 수 없습니다.")
    private String title; // 제목

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category; // 카테고리

    @NotBlank(message = "리뷰를 작성해주세요.")
    @Length(min = 20, message = "리뷰를 최소 20자 이상 작성해주세요.")
    private String description; // 설명

    private int thumbnailIdx; // 썸네일 번호

    private int rating; // 별점

    private List<String> tagNames = new ArrayList<>(); // 태그 이름 목록

    @Builder
    public BoardDto(List<MultipartFile> file, String title, String category, String description, int thumbnailIdx, int rating, List<String> tagNames) {
        this.file = file;
        this.title = title;
        this.category = category;
        this.description = description;
        this.thumbnailIdx = thumbnailIdx;
        this.rating = rating;
        this.tagNames = tagNames;
    }
}
