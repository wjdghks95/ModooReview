package com.io.rol.service;

import com.io.rol.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<Image> saveImages(List<MultipartFile> multipartFileList) throws IOException;
    Image saveImage(MultipartFile multipartFile) throws IOException;
    List<Image> findImages();
}
