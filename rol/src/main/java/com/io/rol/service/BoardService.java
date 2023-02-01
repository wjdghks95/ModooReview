package com.io.rol.service;

import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Member;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Long write(BoardDto boardDto, Member writer) throws IOException;
    Board findBoard(Long id);
    boolean isLike(Long memberId, Long boardId);
    void like(Member member, Board board);

    Page<Board> getList(Pageable pageable, String category, String keyword);

    List<Board> getListBySort(OrderSpecifier<?> orderSpecifier);

    void incrementViews(Board board);
}
