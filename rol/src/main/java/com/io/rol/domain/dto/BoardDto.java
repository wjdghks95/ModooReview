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

@Getter @Setter
@NoArgsConstructor
public class BoardDto {

    private List<MultipartFile> file = new ArrayList<>();

    @NotBlank(message = "제목을 작성해주세요.")
    @Length(max = 50, message = "제목은 50자 이상일 수 없습니다.")
    private String title;

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category;

    @NotBlank(message = "리뷰를 작성해주세요.")
    @Length(min = 20, message = "리뷰를 최소 20자 이상 작성해주세요.")
    private String description;

    private int thumbnailIdx;

    private int rating;

    private List<String> tagNames = new ArrayList<>();

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
