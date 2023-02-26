package com.io.rol.respository;

import com.io.rol.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String tagName); // 태그 이름으로 조회
    boolean existsByName(String tagName); // 태그 이름 존재 여부
}
