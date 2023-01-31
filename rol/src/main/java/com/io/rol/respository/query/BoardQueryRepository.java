package com.io.rol.respository.query;

import com.io.rol.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardQueryRepository {
    Page<Board> findAllPagingByKeyword(Pageable pageable, String category, String keyword);
}
