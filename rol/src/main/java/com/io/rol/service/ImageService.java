package com.io.rol.service;

import com.io.rol.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// 리뷰 이미지 서비스
public interface ImageService {

    // 모든 이미지 저장
    List<Image> saveImages(List<MultipartFile> multipartFileList) throws IOException;

    // 이미지 저장
    Image saveImage(MultipartFile multipartFile) throws IOException;

    // 모든 이미지 조회
    List<Image> findImages();

    // 이미지 전체 삭제
    void deleteImages(List<Image> images);
}
