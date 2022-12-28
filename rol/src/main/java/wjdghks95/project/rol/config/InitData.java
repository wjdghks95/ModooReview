package wjdghks95.project.rol.config;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import wjdghks95.project.rol.domain.dto.ReviewDto;
import wjdghks95.project.rol.domain.entity.Category;
import wjdghks95.project.rol.domain.entity.CategoryName;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.repository.CategoryRepository;
import wjdghks95.project.rol.repository.MemberRepository;
import wjdghks95.project.rol.service.ReviewService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitData {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ReviewService reviewService;
    @Value("classpath:${resource.path.image.sampleImg}")
    Resource resource;

    @PostConstruct
    public void init() throws IOException {

        Member member = Member.builder()
                .phone("010-1234-5678")
                .email("test@test.com")
                .password(passwordEncoder.encode("asdf1234!"))
                .name("test")
                .nickname("테스터")
                .zipcode("12345")
                .address("테스트주소")
                .detailAddress("테스트상세주소")
                .role("ROLE_USER")
                .profileImage(null)
                .build();

        Member savedMember = memberRepository.save(member);

        CategoryName[] categoryNames = CategoryName.values();
        for (CategoryName categoryName : categoryNames) {
            Category category = Category.builder()
                    .categoryName(categoryName)
                    .build();

            categoryRepository.save(category);
        }

        for (int i=0; i < 20; i++) {
            writeReview(member, "식품", 5, "food", "내용1");
            writeReview(member, "코스메틱", 4, "cosmetic", "내용2");
            writeReview(member, "패션", 3, "fashion", "내용3");
            writeReview(member, "출산", 2, "nursing", "내용4");
        }
    }

    private void writeReview(Member savedMember, String title, int rating, String category, String content) throws IOException {
        ReviewDto reviewDto = new ReviewDto();

        File file = new File(resource.getURI());
        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = fileItem.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(multipartFile);
        reviewDto.setMultipartFiles(multipartFiles);

        reviewDto.setTitle(title);
        reviewDto.setRating(rating);
        reviewDto.setCategoryName(category);
        reviewDto.setDescription(content);

        List<String> tagNames = new ArrayList<>();
        tagNames.add("태그1");
        reviewDto.setTagNames(tagNames);

        reviewService.write(reviewDto, savedMember);
    }
}
