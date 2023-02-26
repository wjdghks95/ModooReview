package com.io.rol.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SMSControllerTest {

    @Autowired MockMvc mockMvc;

    private static String sendSmsURL = "/sendSMS";
    private String phoneNumber = "01012345678";

    /*
       SMS 인증번호 발송
          인증번호 발송 버튼 클릭시 /signUp/sendSMS 로 전달
          파라미터로 휴대폰 번호를 같이 전달
          4자리의 임의의 수 반환
     */
    @Test
    @DisplayName("SMS 인증번호 발송")
    void sendSms() throws Exception {
        // given
        mockMvc.perform(get(sendSmsURL + "?phone=" + phoneNumber)
                        .accept(MediaType.APPLICATION_FORM_URLENCODED))
                // when, then
                .andExpect(status().isOk())
                .andDo(print());
    }
}