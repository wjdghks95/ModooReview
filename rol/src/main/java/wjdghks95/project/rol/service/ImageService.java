package wjdghks95.project.rol.service;

import org.springframework.web.multipart.MultipartFile;
import wjdghks95.project.rol.domain.entity.Image;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<Image> saveImages(List<MultipartFile> multipartFileList) throws IOException;
    List<Image> findImages();
    void deleteImages(List<Image> images);
}
