package com.io.rol.Image.controller;

import com.io.rol.Image.service.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileStore fileStore;

    // 이미지 불러오기
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws IOException {
        return new UrlResource("file:" + fileStore.createPath(filename));
    }
}
