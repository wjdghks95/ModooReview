package com.io.rol.comment.service;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.comment.domain.entity.Comment;
import com.io.rol.comment.exception.CommentException;
import com.io.rol.comment.exception.CommentExceptionType;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.comment.repository.CommentRepository;
import com.io.rol.comment.repository.CommentQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepositoryImpl commentQueryRepository;

    // 댓글 저장
    @Transactional
    @Override
    public Long save(Board board, Member member, String content) {
        Comment comment = Comment.builder().content(content).build();
        comment.setBoard(board); // 연관관계 매핑
        comment.setMember(member); // 연관관계 매핑
        return commentRepository.save(comment).getId();
    }

    // 댓글 목록 조회(생성일 역순으로 조회)
    @Override
    public List<Comment> getCommentList(Long id) {
        return commentQueryRepository.findCommentListByBoardId(id);
    }

    // 댓글 삭제
    @Transactional
    @Override
    public void remove(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentExceptionType.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }
}
