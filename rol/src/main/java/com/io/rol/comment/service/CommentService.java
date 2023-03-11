package com.io.rol.comment.service;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.comment.domain.entity.Comment;
import com.io.rol.member.domain.entity.Member;

import java.util.List;

public interface CommentService {
    Long save(Board board, Member member, String content); // 댓글 저장
    List<Comment> getCommentList(Long id); // 댓글 목록 조회(생성일 역순으로 조회)
    void remove(Long id); // 댓글 삭제
}
