package com.io.rol.common.config;

import com.io.rol.category.domain.entity.Category;
import com.io.rol.category.domain.entity.CategoryName;
import com.io.rol.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.*;

@Configuration
public class InitDB {

    @Autowired CategoryRepository categoryRepository;

    @PostConstruct
    public void init() throws IOException {
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
        categoryRepository.save(createCategory(CategoryName.ETC));
        categoryRepository.save(createCategory(CategoryName.CULTURE));
        categoryRepository.save(createCategory(CategoryName.INTERIOR));
    }

    // 카테고리 생성
    private Category createCategory(CategoryName categoryName) {
        return Category.builder().name(categoryName.getCategory()).build();
    }
}
