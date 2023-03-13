package com.io.rol.Image.service;

import com.io.rol.Image.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<Image> saveImages(List<MultipartFile> multipartFileList); // 모든 이미지 저장
    Image saveImage(MultipartFile multipartFile) throws IOException; // 이미지 저장

    // 이미지 전체 삭제
    void deleteImages(List<Image> images);
}
