package com.io.rol.tag.repository;

import com.io.rol.tag.domain.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
}
