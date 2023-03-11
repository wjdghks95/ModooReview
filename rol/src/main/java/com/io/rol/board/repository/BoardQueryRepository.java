package com.io.rol.board.repository;

import com.io.rol.board.domain.entity.Board;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {
    Page<Board> findPagingBoardList(Pageable pageable, String category, String keyword); // 리뷰 목록을 페이징 처리하여 조회

    // 지정한 필드에 따른 순서로 게시글 전체 조회
    List<Board> findAllByOrder(OrderSpecifier<?> orderSpecifier);
}
