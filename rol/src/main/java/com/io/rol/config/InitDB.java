package com.io.rol.config;

import com.io.rol.domain.Role;
import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Category;
import com.io.rol.domain.entity.CategoryName;
import com.io.rol.domain.entity.Member;
import com.io.rol.respository.CategoryRepository;
import com.io.rol.respository.MemberRepository;
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
    @Value("classpath:/static/img/sample1.jpg")
    Resource resource;

    @PostConstruct
    public void init() throws IOException {
        Member member = Member.builder()
                .phone("01012345678")
                .email("user@test.com")
                .password(passwordEncoder.encode("asdf1234!"))
                .name("이름")
                .nickname("닉네임")
                .zipcode("12345")
                .address("경기도 고양시 일산서구 현중로 10")
                .detailAddress("101동 101호")
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        /**
         * 카테고리 저장
         */
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

        File file = new File(resource.getURI());
        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = fileItem.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(multipartFile);

        List<String> tagNames = new ArrayList<>();
        tagNames.add("태그1");
        tagNames.add("태그2");
        tagNames.add("태그3");

        for (int i = 0; i < 15; i++) {
            boardService.write(new BoardDto(multipartFiles, "음식", "food", "음식", 0, 3, tagNames), member);
            boardService.write(new BoardDto(multipartFiles, "미용", "beauty", "미용", 0, 4, tagNames), member);
            boardService.write(new BoardDto(multipartFiles, "코스메틱", "cosmetic", "코스메틱", 0, 1, tagNames), member);
            boardService.write(new BoardDto(multipartFiles, "패션/잡화", "fashion", "패션/잡화", 0, 0, tagNames), member);
            boardService.write(new BoardDto(multipartFiles, "출산/육아", "nursing", "출산/육아", 0, 5, tagNames), member);
        }
    }

    /**
     * 카테고리 생성
     */
    private Category createCategory(CategoryName categoryName) {
        return Category.builder().name(categoryName.getCategory()).build();
    }
}
