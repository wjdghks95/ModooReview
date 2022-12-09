package com.io.rol.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class BoardDto {

    private List<MultipartFile> multipartFiles = new ArrayList<>();

    private String title;

    private String category;

    private String description;

    private int thumbnailIdx;

    private int rating;

    private List<String> tagNames = new ArrayList<>();
}
