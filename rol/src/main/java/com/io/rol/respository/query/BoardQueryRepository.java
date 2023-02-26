package com.io.rol.respository.query;

import com.io.rol.domain.entity.Board;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {
    // 키워드나 카테고리에 맞는 게시글 목록을 페이징 처리하여 조회
    Page<Board> findAllPagingByKeyword(Pageable pageable, String category, String keyword);

    // 지정한 필드에 따른 순서로 게시글 전체 조회
    List<Board> findAllByOrder(OrderSpecifier<?> orderSpecifier);
}
