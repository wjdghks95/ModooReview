package com.io.rol.service;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.domain.entity.Comment;
import com.io.rol.member.domain.entity.Member;

import java.util.List;

// 댓글 서비스
public interface CommentService {
    // 댓글 저장
    Long insert(Board board, Member member, String content);

    // 댓글 목록 조회(생성일 역순으로 조회)
    List<Comment> getList(Long id);

    // 댓글 삭제
    void remove(Long id);
}
