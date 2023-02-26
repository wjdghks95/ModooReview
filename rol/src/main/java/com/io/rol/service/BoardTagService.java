package com.io.rol.service;

import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.BoardTag;
import com.io.rol.domain.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// ManyToMany 양방향 매핑을 위한 BoardTag 서비스
public interface BoardTagService {

    // ManyToMany 양방향 매핑을 위한 BoardTag 엔티티 저장
    List<BoardTag> saveBoardTags(List<Tag> tags, Board board);

    // tagName 에 해당하는 BoardTag 페이징 목록 조회
    Page<BoardTag> getListByTagName(Pageable pageable, String tagName);
}
