package com.io.rol.domain.dto;

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

    private List<MultipartFile> multipartFiles = new ArrayList<>();

    @NotBlank(message = "제목을 작성해주세요.")
    @Length(max = 50, message = "제목은 50자 이상일 수 없습니다.")
    private String title;

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category;

    @Length(min = 20, message = "리뷰를 최소 20자 이상 작성해주세요.")
    private String description;

    private int thumbnailIdx;

    private int rating;

    private List<String> tagNames = new ArrayList<>();
}
