package com.io.rol.service.impl;

import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Comment;
import com.io.rol.domain.entity.Member;
import com.io.rol.respository.CommentRepository;
import com.io.rol.respository.MemberRepository;
import com.io.rol.security.context.MemberContext;
import com.io.rol.service.BoardService;
import com.io.rol.service.CommentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    MemberRepository memberRepository;

    private static String USERNAME = "user@test.com";
    private static String CONTENT = "댓글";

    private void clear(){
        em.flush();
        em.clear();
    }

    @BeforeEach
    private void setAuthentication() throws Exception {
        Member member = memberRepository.findByEmail(USERNAME).orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().value()));

        MemberContext memberContext = new MemberContext(member, roles);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(memberContext, null, roles));
        SecurityContextHolder.setContext(securityContext);
        clear();
    }

    /**
     * 댓글 작성
     *    댓글 작성시 내용을 입력하지 않으면 오류
     */
    @Test
    @DisplayName("댓글 작성")
    void addComment() {
        // given
        Board board = boardService.findBoard(1L);
        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // when
        Long id = commentService.insert(board, memberContext.getMember(), CONTENT);
        clear();

        // then
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("NoSuchElementException"));
        assertNotNull(comment);
        assertEquals(id, comment.getId());
        assertEquals(CONTENT, comment.getContent());
    }

    @Test
    @DisplayName("댓글 작성_실패_내용을 입력하지 않음")
    void addCommentFailure() {
        // given
        Board board = boardService.findBoard(1L);
        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // when, then
        assertThrows(Exception.class, () -> commentService.insert(board, memberContext.getMember(), null));
    }

    /**
     * 댓글 삭제
     */
    @Test
    @DisplayName("댓글 삭제")
    void removeComment() {
        // given
        Board board = boardService.findBoard(1L);
        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = commentService.insert(board, memberContext.getMember(), CONTENT);
        clear();

        // when
        commentService.remove(id);
        clear();

        // then
        assertThat(commentService.getList(board.getId()).size()).isSameAs(0);
        assertThat(assertThrows(NoSuchElementException.class, () -> commentRepository.findById(id).orElseThrow(() -> new NoSuchElementException())));
    }
}