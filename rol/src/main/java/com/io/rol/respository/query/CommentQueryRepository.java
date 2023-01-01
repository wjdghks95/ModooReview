package com.io.rol.respository.query;

import com.io.rol.domain.entity.Comment;

import java.util.List;

public interface CommentQueryRepository {
    List<Comment> findAllByIdOrderByCreatedDate(Long id);
}
