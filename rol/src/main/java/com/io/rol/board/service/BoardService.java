package com.io.rol.board.service;

import com.io.rol.board.domain.dto.BoardDto;
import com.io.rol.board.domain.entity.Board;
import com.io.rol.member.domain.entity.Member;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Long write(BoardDto boardDto, Member writer) throws IOException; // 리뷰 작성

    // 게시글 단건 조회
    Board findBoard(Long id);

    /*
        좋아요 여부
        로그인한 member가 현재 게시글을 좋아요 하지 않은 경우 true
     */
    boolean isLike(Long memberId, Long boardId);

    // 좋아요
    void like(Member member, Board board);

    /*
       게시글 목록 페이징 조회
         - 카테고리 또는 키워드가 있는 경우 해당 목록 페이징 조회
     */
    Page<Board> getList(Pageable pageable, String category, String keyword);

    // 정렬된 게시글 목록 조회
    List<Board> getListBySort(OrderSpecifier<?> orderSpecifier);

    // 조회수 증가
    void incrementViews(Board board);

    // 게시글 수정
    void edit(Board board, BoardDto boardDto) throws IOException;

    // 게시글 삭제
    void remove(Board board);
}
