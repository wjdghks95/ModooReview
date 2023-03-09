package com.io.rol.Image.service;

import com.io.rol.Image.domain.entity.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ImageServiceTest {

    @Autowired ImageService imageService;

    @Value("${file.dir}/")
    private String fileDirPath;

    private static final String IMAGE1 = "sample1.png";
    private static final String IMAGE2= "sample2.png";

    // 이미지 저장
    @Test
    @DisplayName("이미지 저장_성공")
    void save_Image_success() throws IOException {
        // given
        List<MultipartFile> mockUploadFiles = getMockUploadFiles();

        // when
        List<Image> images = imageService.saveImages(mockUploadFiles);

        // then
        assertEquals(images.size(), mockUploadFiles.size());
        images.stream().forEach(image -> {
            File file = new File(fileDirPath + image.getStoreFileName());
            assertTrue(file.exists());
            file.delete();
        });
    }

    private List<MultipartFile> getMockUploadFiles() throws IOException {
        List<MultipartFile> multipartFileList = new ArrayList<>();
        MultipartFile mockUploadFile1 = getMockUploadFile(IMAGE1);
        MultipartFile mockUploadFile2 = getMockUploadFile(IMAGE2);
        multipartFileList.add(mockUploadFile1);
        multipartFileList.add(mockUploadFile2);
        return multipartFileList;
    }

    private MockMultipartFile getMockUploadFile(String fileName) throws IOException {
        return new MockMultipartFile("file", "file.jpg", "image/jpg",
                new FileInputStream(fileDirPath + fileName));
    }
}