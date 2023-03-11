package com.io.rol.comment.controller;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.board.repository.BoardRepository;
import com.io.rol.comment.domain.entity.Comment;
import com.io.rol.comment.repository.CommentRepository;
import com.io.rol.member.domain.dto.MemberDto;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.exception.MemberException;
import com.io.rol.member.exception.MemberExceptionType;
import com.io.rol.member.repository.MemberRepository;
import com.io.rol.member.service.MemberService;
import com.io.rol.security.context.MemberContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired EntityManager em;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired BoardRepository boardRepository;
    @Autowired CommentRepository commentRepository;
    private static final String USERNAME = "test@test.com";
    private static final String ADD_COMMENT_URL = "/api/comment";
    private static final String DEL_COMMENT_URL = "/api/comment";
    private static final String CONTENT = "댓글";

    private void clear(){
        em.flush();
        em.clear();
    }

    @BeforeEach
    private void setAuthentication() throws Exception {
        Board board = Board.builder().title("테스트").description("테스트").build();
        boardRepository.save(board);
        memberService.join(createMemberDto());
        Member member = memberRepository.findByEmail(USERNAME).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

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
                .email(USERNAME)
                .password("asdf1234!")
                .name("이름")
                .nickname("테스트")
                .zipcode("12345")
                .address("주소")
                .detailAddress("상세주소")
                .build();
    }

    // 댓글 작성
    @Test
    @DisplayName("댓글 작성_성공")
    void addComment_success() throws Exception {
        // given, when
        newComment(CONTENT)
                // then
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void delComment() throws Exception {
        // given
        Comment comment = Comment.builder().content(CONTENT).build();
        commentRepository.save(comment);
        clear();

        // when
        delComment(comment)
                // then
                .andExpect(status().isOk())
                .andDo(print());
    }

    private ResultActions newComment(String content) throws Exception {
        return mockMvc.perform(post(ADD_COMMENT_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("content", content)
                .param("id", "1")
                .with(csrf()));
    }

    private ResultActions delComment(Comment comment) throws Exception {
        return mockMvc.perform(delete(DEL_COMMENT_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("index", String.valueOf(comment.getId()))
                .param("id", "1")
                .with(csrf()));
    }
}