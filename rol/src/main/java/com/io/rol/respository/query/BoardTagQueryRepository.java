package com.io.rol.respository.query;

import com.io.rol.domain.entity.BoardTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardTagQueryRepository {
    // 태그 이름으로 페이징 처리 후 조회
    Page<BoardTag> findBoardTagByTagName(Pageable pageable, String tagName);
}
