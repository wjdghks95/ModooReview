package com.io.rol.service.impl;

import com.io.rol.domain.entity.Image;
import com.io.rol.respository.ImageRepository;
import com.io.rol.service.FileStore;
import com.io.rol.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    public List<Image> saveImages(List<MultipartFile> files) throws IOException {
        List<Image> images = fileStore.storeFiles(files);
        return imageRepository.saveAll(images);
    }

    /**
     * 이미지 저장
     */
    @Override
    @Transactional
    public Image saveImage(MultipartFile multipartFile) throws IOException {
        Image image = fileStore.storeFile(multipartFile);
        return imageRepository.save(image);
    }

    /**
     * 모든 이미지 조회
     */
    @Override
    public List<Image> findImages() {
        return imageRepository.findAll();
    }

    /**
     * 이미지 삭제
     */
    @Override
    @Transactional
    public void deleteImages(List<Image> images) {
        images.forEach(image -> {
            new File(fileStore.createPath(image.getStoreFileName())).delete();
            imageRepository.delete(image);
        });
    }
}