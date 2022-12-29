package com.io.rol.service;

import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Member;

import java.io.IOException;

public interface BoardService {

    Long write(BoardDto boardDto, Member writer) throws IOException;
    Board findBoard(Long id);
}
