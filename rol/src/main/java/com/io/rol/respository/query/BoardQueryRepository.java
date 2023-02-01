package com.io.rol.respository.query;

import com.io.rol.domain.entity.Board;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {
    Page<Board> findAllPagingByKeyword(Pageable pageable, String category, String keyword);

    List<Board> findAllByOrder(OrderSpecifier<?> orderSpecifier);
}
