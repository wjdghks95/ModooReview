package com.io.rol.tag.service;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.tag.domain.entity.BoardTag;
import com.io.rol.tag.domain.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardTagService {

    List<BoardTag> saveBoardTags(List<Tag> tags, Board board); // BoardTag 저장
    Page<BoardTag> getBoardTagList(Pageable pageable, String tagName); // tagName에 해당하는 BoardTag 페이징 목록 조회
}
