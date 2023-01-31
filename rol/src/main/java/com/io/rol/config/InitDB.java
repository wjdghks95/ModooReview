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

        Member savedMember = memberRepository.save(member);

        /**
         * 카테고리 저장
         */
        categoryRepository.save(createCategory(CategoryName.FOOD));
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

        for (int i = 0; i < 5; i++) {
            write(savedMember, "FOOD", 5, "food", "내용1");
            write(savedMember, "COSMETIC", 4, "cosmetic", "내용2");
            write(savedMember, "FASHION", 3, "fashion", "내용3");
            write(savedMember, "NURSING", 2, "nursing", "내용4");
            write(savedMember, "DIGITAL", 1, "digital", "내용5");
            write(savedMember, "SPORTS", 0, "sports", "내용6");
            write(savedMember, "PET", 1, "pet", "내용7");
            write(savedMember, "RESTAURANT", 2, "restaurant", "내용8");
            write(savedMember, "TRAVEL", 3, "travel", "내용9");
            write(savedMember, "CULTURE", 4, "culture", "내용10");
            write(savedMember, "ETC", 5, "etc", "내용11");
        }
    }

    /**
     * 카테고리 생성
     */
    private Category createCategory(CategoryName categoryName) {
        return Category.builder().name(categoryName.getCategory()).build();
    }

    /**
     * 게시글 작성
     */
    private void write(Member savedMember, String title, int rating, String category, String content) throws IOException {
        File file = new File(resource.getURI());
        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = fileItem.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(multipartFile);

        BoardDto boardDto = new BoardDto();
        boardDto.setFile(multipartFiles);
        boardDto.setTitle(title);
        boardDto.setRating(rating);
        boardDto.setCategory(category);
        boardDto.setDescription(content);

        List<String> tagNames = new ArrayList<>();
        tagNames.add("태그1");
        tagNames.add("태그2");
        boardDto.setTagNames(tagNames);

        boardService.write(boardDto, savedMember);
    }
}
