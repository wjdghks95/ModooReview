package com.io.rol.service;

import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.BoardTag;
import com.io.rol.domain.entity.Tag;

import java.util.List;

public interface BoardTagService {

    List<BoardTag> saveBoardTags(List<Tag> tags, Board board);
}
