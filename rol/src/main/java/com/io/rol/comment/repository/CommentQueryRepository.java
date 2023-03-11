package com.io.rol.comment.repository;

import com.io.rol.comment.domain.entity.Comment;

import java.util.List;

public interface CommentQueryRepository {
    List<Comment> findCommentListByBoardId(Long id); // 해당 게시글의 댓글 역순 정렬
}
