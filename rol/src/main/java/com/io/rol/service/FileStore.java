package com.io.rol.service;

import com.io.rol.domain.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${resource.path.fileStore}/")
    private String fileDirPath;

    /** 확장자 추출 */
    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }

    /** 저장할 파일 이름 구성 */
    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;
        return storeFilename;
    }

    /** 파일 저장 경로 */
    public String createPath(String storeFilename) {
        return fileDirPath + storeFilename;
    }

    /** 파일 저장 */
    public Image storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename(); // 원본 파일 이름
        String storeFilename = createStoreFilename(originalFilename); // store 파일 이름
        file.transferTo(new File(createPath(storeFilename))); // 파일 저장

        return Image.builder()
                .originFileName(originalFilename)
                .storeFileName(storeFilename)
                .build();
    }

    /** 전체 파일 저장 */
    public List<Image> storeFiles(List<MultipartFile> files) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                images.add(storeFile(file));
            }
        }
        return images;
    }
}