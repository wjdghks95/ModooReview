package com.io.rol.category.repository;

import com.io.rol.category.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name); // 카테고리 이름으로 조회
}
