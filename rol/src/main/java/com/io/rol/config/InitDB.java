package com.io.rol.config;

import com.io.rol.domain.Role;
import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Category;
import com.io.rol.domain.entity.CategoryName;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.respository.CategoryRepository;
import com.io.rol.member.repository.MemberRepository;
import com.io.rol.service.BoardService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitDB {

    @Autowired MemberRepository memberRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired BoardService boardService;
    @Autowired PasswordEncoder passwordEncoder;
    @Value("classpath:/static/img/")
    Resource resource;

//    @PostConstruct
    public void init() throws IOException {
        // 관리자 저장
        Member member = Member.builder()
                .phone("01027999308")
                .email("admin@admin.com")
                .password(passwordEncoder.encode("asdf1234!"))
                .name("이정환")
                .nickname("관리자")
                .zipcode("12345")
                .address("경기도 고양시 일산서구 탄중로501")
                .detailAddress("504동 602호")
                .role(Role.ADMIN)
                .build();

        memberRepository.save(member);

        // 카테고리 저장
        categoryRepository.save(createCategory(CategoryName.FOOD));
        categoryRepository.save(createCategory(CategoryName.BEAUTY));
        categoryRepository.save(createCategory(CategoryName.COSMETIC));
        categoryRepository.save(createCategory(CategoryName.FASHION));
        categoryRepository.save(createCategory(CategoryName.NURSING));
        categoryRepository.save(createCategory(CategoryName.DIGITAL));
        categoryRepository.save(createCategory(CategoryName.SPORTS));
        categoryRepository.save(createCategory(CategoryName.PET));
        categoryRepository.save(createCategory(CategoryName.RESTAURANT));
        categoryRepository.save(createCategory(CategoryName.TRAVEL));
        categoryRepository.save(createCategory(CategoryName.CULTURE));
        categoryRepository.save(createCategory(CategoryName.INTERIOR));
        categoryRepository.save(createCategory(CategoryName.ETC));

        List<String> review1 = new ArrayList<>();
        review1.add("review1_1.png");
        review1.add("review1_2.png");
        review1.add("review1_3.png");
        List<MultipartFile> multipartFiles1 = createFiles(review1);

        List<String> review1Tag = new ArrayList<>();
        review1Tag.add("미용");
        review1Tag.add("샴푸");
        review1Tag.add("컴백퍼플보색샴푸");

        boardService.write(new BoardDto(multipartFiles1, "탈색모에 필수인 보색샴푸 추천", "beauty",
                "탈색모에 필수인 보색샴푸 추천", 0, 3, review1Tag), member);

        List<String> review2 = new ArrayList<>();
        review2.add("review2_1.png");
        review2.add("review2_2.png");
        List<MultipartFile> multipartFiles2 = createFiles(review2);

        List<String> review2Tag = new ArrayList<>();
        review2Tag.add("패션");
        review2Tag.add("가방");
        review2Tag.add("힙색");
        review2Tag.add("리나일론허리가방");

        boardService.write(new BoardDto(multipartFiles2, "남자 힙색 추천 지쿤 힙한 리나일론 허리가방", "fashion",
                "남자 힙색 추천 지쿤 힙한 리나일론 허리가방", 0, 5, review2Tag), member);

        List<String> review3 = new ArrayList<>();
        review3.add("review3_1.png");
        review3.add("review3_2.png");
        review3.add("review3_3.png");
        review3.add("review3_4.png");
        List<MultipartFile> multipartFiles3 = createFiles(review3);

        List<String> review3Tag = new ArrayList<>();
        review3Tag.add("음식");
        review3Tag.add("오리고추장주물럭");
        review3Tag.add("힙색");
        review3Tag.add("리나일론 허리가방");

        boardService.write(new BoardDto(multipartFiles3, "구월동맛집 구월오리 :: 오리주물럭을 밀키트로", "food",
                "구월동맛집 구월오리 :: 오리주물럭을 밀키트로", 2, 4, review3Tag), member);

    }

    // MultipartFiles 생성
    private List<MultipartFile> createFiles(List<String> fileNameList) throws IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        for (String fileName : fileNameList) {
            String uri = resource.getURI().getPath() + fileName;
            File file = new File(uri);
            FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = fileItem.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            multipartFiles.add(multipartFile);
        }

        return multipartFiles;
    }

    // 카테고리 생성
    private Category createCategory(CategoryName categoryName) {
        return Category.builder().name(categoryName.getCategory()).build();
    }
}
