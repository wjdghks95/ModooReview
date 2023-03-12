package com.io.rol.tag.repository;

import com.io.rol.tag.domain.entity.BoardTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardTagQueryRepository {
    Page<BoardTag> findBoardTagListByTagName(Pageable pageable, String tagName); // 태그 이름으로 페이징하여 조회
}
