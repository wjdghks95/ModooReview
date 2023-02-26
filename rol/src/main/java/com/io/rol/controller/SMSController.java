package com.io.rol.controller;

import com.io.rol.service.SMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 문자 인증 컨트롤러
@RestController
@RequiredArgsConstructor
@Slf4j
public class SMSController {

    private final SMSService smsService;

    /*
       coolsms api 를 이용한 SMS 문자 인증
       인증번호 발송 버튼 클릭시 비동기로 전달
     */
    @GetMapping("/sendSMS")
    public String sendSMS(@RequestParam("phone") String phoneNumber) {
        int randomNumber = (int)((Math.random() * (9999 - 1000 + 1)) + 1000); // 난수 생성

        smsService.certifiedPhoneNumber(phoneNumber, randomNumber);
        log.info("randomNumber: {}", randomNumber);
        return Integer.toString(randomNumber); // data 반환
    }
}
