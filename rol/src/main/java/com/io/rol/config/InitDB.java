package com.io.rol.config;

import com.io.rol.domain.dto.MemberDto;
import com.io.rol.domain.entity.Category;
import com.io.rol.domain.entity.CategoryName;
import com.io.rol.respository.CategoryRepository;
import com.io.rol.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class InitDB {

    @Autowired MemberService memberService;
    @Autowired CategoryRepository categoryRepository;

    @PostConstruct
    public void saveMember() {
        MemberDto memberDto = MemberDto.builder()
                .phone("01012345678")
                .email("test@test.com")
                .password("asdf1234!")
                .name("이름")
                .nickname("닉네임")
                .zipcode("12345")
                .address("경기도 고양시 일산서구 현중로 10")
                .detailAddress("101동 101호")
                .build();

        memberService.join(memberDto);
    }

    /**
     * 카테고리 저장
     */
    @PostConstruct
    public void saveCategory() {
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
    }

    /**
     * 카테고리 생성
     */
    private Category createCategory(CategoryName categoryName) {
        return Category.builder().name(categoryName.getCategory()).build();
    }
}
