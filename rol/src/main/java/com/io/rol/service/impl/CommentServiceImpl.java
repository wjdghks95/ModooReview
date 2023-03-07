package com.io.rol.service.impl;

import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Comment;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.respository.query.CommentQueryRepositoryImpl;
import com.io.rol.respository.CommentRepository;
import com.io.rol.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

// 댓글 서비스
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepositoryImpl commentQueryRepository;

    // 댓글 저장
    @Transactional
    @Override
    public Long insert(Board board, Member member, String content) {
        Comment comment = Comment.builder().content(content).build();
        comment.setBoard(board); // 연관관계 매핑
        comment.setMember(member); // 연관관계 매핑
        return commentRepository.save(comment).getId();
    }

    // 댓글 목록 조회(생성일 역순으로 조회)
    @Override
    public List<Comment> getList(Long id) {
        return commentQueryRepository.findAllByIdOrderByCreatedDate(id);
    }

    // 댓글 삭제
    @Transactional
    @Override
    public void remove(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("NoSuchElementException"));
        commentRepository.delete(comment);
    }
}
