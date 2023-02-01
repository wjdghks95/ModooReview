package com.io.rol.respository.query;

import com.io.rol.domain.entity.BoardTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardTagQueryRepository {
    Page<BoardTag> findBoardTagByTagName(Pageable pageable, String tagName);
}
