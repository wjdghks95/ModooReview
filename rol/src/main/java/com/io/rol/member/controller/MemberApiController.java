package com.io.rol.member.controller;

import com.io.rol.member.service.SMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final SMSService smsService;

    /*
       coolsms api를 이용한 SMS 문자 인증
       인증번호 발송 버튼 클릭시 비동기로 전달
     */
    @GetMapping("/api/SMS")
    public String sendSMS(@RequestParam("phoneNum") String phoneNumber) {
        int randomNumber = smsService.certifiedPhoneNumber(phoneNumber);
        log.info("randomNumber={}", randomNumber);

        return Integer.toString(randomNumber); // data 반환
    }
}
