package com.io.rol.service;

import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Comment;
import com.io.rol.domain.entity.Member;

import java.util.List;

public interface CommentService {
    Long insert(Board board, Member member, String content);
    List<Comment> getList(Long id);
    void remove(Long id);
}
