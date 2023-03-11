package com.io.rol.comment.service;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.board.exception.BoardException;
import com.io.rol.board.exception.BoardExceptionType;
import com.io.rol.board.repository.BoardRepository;
import com.io.rol.comment.domain.entity.Comment;
import com.io.rol.comment.exception.CommentException;
import com.io.rol.comment.exception.CommentExceptionType;
import com.io.rol.comment.repository.CommentRepository;
import com.io.rol.member.domain.dto.MemberDto;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.service.MemberService;
import com.io.rol.security.context.MemberContext;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceImplTest {
    @Autowired EntityManager em;
    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired MemberService memberService;
    @Autowired BoardRepository boardRepository;

    private static final String USERNAME = "test@test.com";
    private static final String CONTENT = "댓글";

    private void clear(){
        em.flush();
        em.clear();
    }

    @BeforeEach
    private void setAuthentication() throws Exception {
        MemberDto memberDto = createMemberDto();
        Long memberId = memberService.join(memberDto);
        Member member = memberService.getMember(memberId);

        Board board = Board.builder().title("테스트").description("테스트").build();
        boardRepository.save(board);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().value()));

        MemberContext memberContext = new MemberContext(member, roles);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(memberContext, null, roles));
        SecurityContextHolder.setContext(securityContext);
        clear();
    }

    private MemberDto createMemberDto() {
        return MemberDto.builder()
                .phone("01012345678")
                .email("test@test.com")
                .password("asdf1234!")
                .name("이름")
                .nickname("테스트")
                .zipcode("12345")
                .address("주소")
                .detailAddress("상세주소")
                .build();
    }

    /*
       댓글 작성
          댓글 작성시 내용을 입력하지 않으면 오류
     */
    @Test
    @DisplayName("댓글 작성_성공")
    void addComment() {
        // given
        Board board = boardRepository.findById(1L).orElseThrow(() -> new BoardException(BoardExceptionType.BOARD_NOT_FOUND));
        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // when
        Long id = commentService.save(board, memberContext.getMember(), CONTENT);
        clear();

        // then
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentExceptionType.COMMENT_NOT_FOUND));
        assertNotNull(comment);
        assertEquals(id, comment.getId());
        assertEquals(CONTENT, comment.getContent());
    }

    @Test
    @DisplayName("댓글 작성_실패_내용을 입력하지 않음")
    void addCommentFailure() {
        // given
        Board board = boardRepository.findById(1L).orElseThrow(() -> new BoardException(BoardExceptionType.BOARD_NOT_FOUND));
        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // when, then
        assertThrows(Exception.class, () -> commentService.save(board, memberContext.getMember(), null));
    }

    // 댓글 삭제
    @Test
    @DisplayName("댓글 삭제")
    void removeComment() {
        // given
        Board board = boardRepository.findById(1L).orElseThrow(() -> new BoardException(BoardExceptionType.BOARD_NOT_FOUND));
        MemberContext memberContext = (MemberContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = commentService.save(board, memberContext.getMember(), CONTENT);
        clear();

        // when
        commentService.remove(id);
        clear();

        // then
        assertThat(commentService.getCommentList(board.getId()).size()).isSameAs(0);
        assertThat(assertThrows(CommentException.class, () -> commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentExceptionType.COMMENT_NOT_FOUND))));
    }
}