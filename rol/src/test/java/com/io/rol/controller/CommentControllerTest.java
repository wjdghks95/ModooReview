package com.io.rol.controller;

import com.io.rol.domain.entity.Comment;
import com.io.rol.domain.entity.Member;
import com.io.rol.respository.CommentRepository;
import com.io.rol.respository.MemberRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;
    @Autowired CommentRepository commentRepository;
    private static String USERNAME = "user@test.com";
    private static String ADD_Comment_URL = "/comment.do";
    private static String DEL_Comment_URL = "/comment.de";
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
     */
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
        return mockMvc.perform(post(ADD_Comment_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("content", content)
                .param("id", "1")
                .with(csrf()));
    }

    private ResultActions delComment(Comment comment) throws Exception {
        return mockMvc.perform(get(DEL_Comment_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("index", String.valueOf(comment.getId()))
                .param("id", "1")
                .with(csrf()));
    }
}