package com.io.rol.service;

import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.BoardTag;
import com.io.rol.domain.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardTagService {

    List<BoardTag> saveBoardTags(List<Tag> tags, Board board);

    Page<BoardTag> getListByTagName(Pageable pageable, String tagName);
}
