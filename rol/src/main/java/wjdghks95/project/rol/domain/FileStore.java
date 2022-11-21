package wjdghks95.project.rol.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import wjdghks95.project.rol.domain.entity.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${resource.path.file}/")
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
    public Image storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); // 원본 파일 이름
        String storeFilename = createStoreFilename(originalFilename); // store 파일 이름
        multipartFile.transferTo(new File(createPath(storeFilename))); // 파일 저장

        return Image.builder()
                .originFileName(originalFilename)
                .storeFileName(storeFilename)
                .build();
    }

    /** 전체 파일 저장 */
    public List<Image> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                images.add(storeFile(multipartFile));
            }
        }
        return images;
    }
}
