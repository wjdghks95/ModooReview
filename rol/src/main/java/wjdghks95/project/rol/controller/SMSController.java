package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wjdghks95.project.rol.service.SMSService;

@RestController
@RequiredArgsConstructor
public class SMSController {

    private final SMSService smsService;

    @GetMapping("/check/sendSMS")
    public String sendSMS(@RequestParam("phone") String phoneNumber) {
        int randomNumber = (int)((Math.random() * (9999 - 1000 + 1)) + 1000); //난수 생성

//        smsService.certifiedPhoneNumber(phoneNumber, randomNumber);
        System.out.println(randomNumber);
        return Integer.toString(randomNumber);
    }
}
