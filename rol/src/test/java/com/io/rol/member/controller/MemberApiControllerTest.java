package com.io.rol.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    private static final String SMS_URL = "/api/SMS";
    private static final String PHONE_NUMBER = "01027999308";

    /*
       SMS 인증번호 발송
          인증번호 발송 버튼 클릭시 /api/SMS 로 전달
          파라미터로 휴대폰 번호를 같이 전달
          4자리의 임의의 수 반환
     */
    @Test
    @DisplayName("SMS 인증번호 발송")
    void sendSms() throws Exception {
        // given
        mockMvc.perform(get(SMS_URL)
                        .accept(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("phoneNum", PHONE_NUMBER))
                // when, then
                .andExpect(status().isOk())
                .andDo(print());
    }
}