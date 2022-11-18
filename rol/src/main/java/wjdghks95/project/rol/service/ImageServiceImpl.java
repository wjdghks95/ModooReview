package wjdghks95.project.rol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wjdghks95.project.rol.domain.FileStore;
import wjdghks95.project.rol.domain.entity.Image;
import wjdghks95.project.rol.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    @Override
    @Transactional
    public List<Image> saveImages(List<MultipartFile> multipartFileList) throws IOException {
        List<Image> images = fileStore.storeFiles(multipartFileList);
        return images;
    }

    @Override
    public List<Image> findImages() {
        List<Image> images = imageRepository.findAll();
        return images;
    }

    @Override
    @Transactional
    public void deleteImages(List<Image> images) {
        images.forEach(image -> {
            new File(fileStore.createPath(image.getStoreFileName())).delete();
            imageRepository.delete(image);
        });
    }
}
