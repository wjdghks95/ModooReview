package com.io.rol.respository.query;

import com.io.rol.comment.domain.entity.Comment;

import java.util.List;

public interface CommentQueryRepository {
    // id에 해당하는 게시글의 댓글 역순 정렬
    List<Comment> findAllByIdOrderByCreatedDate(Long id);
}
