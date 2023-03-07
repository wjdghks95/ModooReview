package com.io.rol.member.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.rol.member.domain.dto.MemberDto;
import com.io.rol.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    EntityManager em;
    @Autowired
    MemberService memberService;
    ObjectMapper objectMapper = new ObjectMapper();

    private static final String PHONE = "01012345678";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "asdf1234!";
    private static final String NAME = "이름";
    private static final String NICKNAME = "테스트";
    private static final String ZIPCODE = "12345";
    private static final String ADDRESS = "주소";
    private static final String DETAIL_ADDRESS = "상세주소";

    private static final String SIGNUP_URL = "/signUp";
    private static final String LOGIN_URL = "/login";

    private void clear(){
        em.flush();
        em.clear();
    }

    /*
       회원가입
          회원가입 시 핸드폰 번호, 이메일, 비밀번호, 이름, 닉네임, 주소를 입력하지 않으면 오류
          이미 존재하는 아이디, 닉네임이 있으면 오류
          잘못된 비밀번호나 닉네임을 입력한 경우 오류
     */
    @Test
    @DisplayName("회원가입_성공")
    void signUp_success() throws Exception {
        //given
        MemberDto memberDto = createMemberDto(PHONE, EMAIL, PASSWORD, NAME, NICKNAME, ZIPCODE, ADDRESS, DETAIL_ADDRESS);

        //when, then
        signUpPerform(memberDto)
                .andExpect(status().is3xxRedirection()) // 성공 시 /login 으로 Redirect
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입_실패_필수 입력란에 입력하지 않는 필드 존재")
    void signUp_failure_empty_field() throws Exception {
        //given
        MemberDto memberDto = createMemberDto("", "", "", "", "", "", "", "");

        //when, then
        signUpPerform(memberDto)
                .andExpect(model().attributeHasFieldErrors("memberDto", "phone"))
                .andExpect(model().attributeHasFieldErrors("memberDto", "email"))
                .andExpect(model().attributeHasFieldErrors("memberDto", "password"))
                .andExpect(model().attributeHasFieldErrors("memberDto", "name"))
                .andExpect(model().attributeHasFieldErrors("memberDto", "nickname"))
                .andExpect(model().attributeHasFieldErrors("memberDto", "zipcode"))
                .andExpect(model().attributeHasFieldErrors("memberDto", "address"))
                .andExpect(status().isOk()) // 필드에 오류가 있어도 상태는 200
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입_실패_잘못된 이메일 입력")
    void signUp_failure_badEmail() throws Exception {
        //given
        MemberDto memberDto = createMemberDto(PHONE, "asdf", PASSWORD, NAME, NICKNAME, ZIPCODE, ADDRESS, DETAIL_ADDRESS);

        //when, then
        signUpPerform(memberDto)
                .andExpect(model().attributeHasFieldErrors("memberDto", "email"))
                .andExpect(status().isOk()) // 필드에 오류가 있어도 상태는 200
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입_실패_잘못된 비밀번호 입력")
    void signUp_failure_badPassword() throws Exception {
        //given
        MemberDto memberDto = createMemberDto(PHONE, EMAIL, "1234", NAME, NICKNAME, ZIPCODE, ADDRESS, DETAIL_ADDRESS);

        //when, then
        signUpPerform(memberDto)
                .andExpect(model().attributeHasFieldErrors("memberDto", "password"))
                .andExpect(status().isOk()) // 필드에 오류가 있어도 상태는 200
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입_실패_잘못된 이름 입력")
    void signUp_failure_badName() throws Exception {
        //given
        MemberDto memberDto = createMemberDto(PHONE, EMAIL, PASSWORD, "ㄱㄴ", NICKNAME, ZIPCODE, ADDRESS, DETAIL_ADDRESS);

        //when, then
        signUpPerform(memberDto)
                .andExpect(model().attributeHasFieldErrors("memberDto", "name"))
                .andExpect(status().isOk()) // 필드에 오류가 있어도 상태는 200
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입_실패_잘못된 닉네임 입력")
    void signUp_failure_badNickname() throws Exception {
        //given
        MemberDto memberDto = createMemberDto(PHONE, EMAIL, PASSWORD, NAME, NICKNAME+"ㄱ", ZIPCODE, ADDRESS, DETAIL_ADDRESS);

        //when, then
        signUpPerform(memberDto)
                .andExpect(model().attributeHasFieldErrors("memberDto", "nickname"))
                .andExpect(status().isOk()) // 필드에 오류가 있어도 상태는 200
                .andDo(print());
    }

    private static MemberDto createMemberDto(String PHONE, String EMAIL, String PASSWORD, String NAME, String NICKNAME, String ZIPCODE, String ADDRESS, String DETAIL_ADDRESS) {
        return MemberDto.builder()
                .phone(PHONE)
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .zipcode(ZIPCODE)
                .address(ADDRESS)
                .detailAddress(DETAIL_ADDRESS)
                .build();
    }

    private ResultActions signUpPerform(MemberDto memberDto) throws Exception {
        MultiValueMap<String, String> params = setParams(memberDto);

        return mockMvc.perform(post(SIGNUP_URL)
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .params(params));
    }

    private MultiValueMap<String, String> setParams(MemberDto memberDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> maps = objectMapper.convertValue(memberDto, new TypeReference<>() {});
        params.setAll(maps);
        return params;
    }

    /*
       로그인
          로그인시 아이디가 존재하지 않을 경우 로그인 실패
          로그인시 비밀번호가 틀리면 로그인 실패
     */
    @Test
    @DisplayName("로그인_성공")
    void login_success() throws Exception {
        // given
        MemberDto memberDto = createMemberDto(PHONE, EMAIL, PASSWORD, NAME, NICKNAME, ZIPCODE, ADDRESS, DETAIL_ADDRESS);
        memberService.join(memberDto);

        // when, then
        loginPerform(EMAIL, PASSWORD)
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인_실패_아이디를 찾을수 없음")
    void login_failure_notFoundUsername() throws Exception {
        // given
        MemberDto memberDto = createMemberDto(PHONE, EMAIL, PASSWORD, NAME, NICKNAME, ZIPCODE, ADDRESS, DETAIL_ADDRESS);
        memberService.join(memberDto);

        // when, then
        loginPerform("hello@test.com", PASSWORD)
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인_실패_패스워드가 틀림")
    void login_failure_badCredentials() throws Exception {
        // given
        MemberDto memberDto = createMemberDto(PHONE, EMAIL, PASSWORD, NAME, NICKNAME, ZIPCODE, ADDRESS, DETAIL_ADDRESS);
        memberService.join(memberDto);

        // when, then
        loginPerform(EMAIL, PASSWORD+"123")
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated())
                .andDo(print());
    }

    private ResultActions loginPerform(String EMAIL, String PASSWORD) throws Exception {
        return mockMvc.perform(post(LOGIN_URL)
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", EMAIL)
                .param("password", PASSWORD)
                .with(csrf()));
    }
}