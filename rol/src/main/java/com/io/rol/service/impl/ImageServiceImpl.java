package com.io.rol.service.impl;

import com.io.rol.domain.entity.Image;
import com.io.rol.respository.ImageRepository;
import com.io.rol.service.FileStore;
import com.io.rol.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    /**
     * 모든 이미지 저장
     */
    @Override
    @Transactional
    public List<Image> saveImages(List<MultipartFile> multipartFileList) throws IOException {
        List<Image> images = fileStore.storeFiles(multipartFileList);
        return imageRepository.saveAll(images);
    }

    /**
     * 모든 이미지 조회
     */
    @Override
    public List<Image> findImages() {
        return imageRepository.findAll();
    }
}