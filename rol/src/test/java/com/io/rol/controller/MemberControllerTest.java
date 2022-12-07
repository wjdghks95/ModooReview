package com.io.rol.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.rol.domain.dto.MemberDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired
    EntityManager em;
    ObjectMapper objectMapper = new ObjectMapper();

    private String phone = "01012345678";
    private String email = "test@test.com";
    private String password = "asdf1234!";
    private String wrongPass = "1234";
    private String name = "이름";
    private String nickname = "테스트";
    private String wrongNick = "ㄱㅏ!@";
    private String zipcode = "12345";
    private String address = "경기도 고양시 일산서구 현중로 10";
    private String detailAddress = "101동 101호";

    private static String SIGN_UP_URL = "/signUp";

    private void clear(){
        em.flush();
        em.clear();
    }

    /**
     * 회원가입
     *    회원가입 시 핸드폰 번호, 이메일, 비밀번호, 이름, 닉네임, 주소를 입력하지 않으면 오류
     *    이미 존재하는 아이디, 닉네임이 있으면 오류
     *    잘못된 비밀번호나 닉네임을 입력한 경우 오류
     */
    @Test
    @DisplayName("회원가입_성공")
    public void signUp_success() throws Exception {
        //given
        MemberDto memberDto = createMemberDto(phone, email, password, name, nickname, zipcode, address, detailAddress);

        //when, then
        perform(memberDto)
                .andExpect(status().is3xxRedirection()) // 성공 시 /login 으로 Redirect
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입_실패_필수 입력란에 입력하지 않는 필드 존재")
    public void signUp_failure_empty_field() throws Exception {
        //given
        MemberDto memberDto = createMemberDto("", "", "", "", "", "", "", "");

        //when, then
        perform(memberDto)
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
    @DisplayName("회원가입_실패_잘못된 비밀번호 입력")
    public void signUp_failure_wrong_password() throws Exception {
        //given
        MemberDto memberDto = createMemberDto(phone, email, wrongPass, name, nickname, zipcode, address, detailAddress);

        //when, then
        perform(memberDto)
                .andExpect(model().attributeHasFieldErrors("memberDto", "password"))
                .andExpect(status().isOk()) // 필드에 오류가 있어도 상태는 200
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입_실패_잘못된 닉네임 입력")
    public void signUp_failure_wrong_nickname() throws Exception {
        //given
        MemberDto memberDto = createMemberDto(phone, email, password, name, wrongNick, zipcode, address, detailAddress);

        //when, then
        perform(memberDto)
                .andExpect(model().attributeHasFieldErrors("memberDto", "nickname"))
                .andExpect(status().isOk()) // 필드에 오류가 있어도 상태는 200
                .andDo(print());
    }

    private static MemberDto createMemberDto(String phone, String email, String password, String name, String nickname, String zipcode, String address, String detailAddress) {
        return MemberDto.builder()
                .phone(phone)
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .zipcode(zipcode)
                .address(address)
                .detailAddress(detailAddress)
                .build();
    }

    private MultiValueMap<String, String> setParams(MemberDto memberDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> maps = objectMapper.convertValue(memberDto, new TypeReference<>() {});
        params.setAll(maps);
        return params;
    }

    private ResultActions perform(MemberDto memberDto) throws Exception {
        MultiValueMap<String, String> params = setParams(memberDto);

        return mockMvc.perform(post(SIGN_UP_URL)
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params));
    }
}