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

    Board getBoard(Long id); // 리뷰 단건 조회

    Page<Board> getBoardList(Pageable pageable, String category, String keyword); // 리뷰 목록 페이징 조회

    boolean isLike(Long memberId, Long boardId); // 좋아요 여부

    void like(Member member, Board board); // 좋아요

    void incrementViews(Board board); // 조회수 증가

    void edit(Board board, BoardDto boardDto) throws IOException; // 게시글 수정

    void remove(Board board); // 게시글 삭제

    List<Board> getBoardList(OrderSpecifier<?> orderSpecifier); // 정렬된 게시글 목록 조회
}
