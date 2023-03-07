package com.io.rol.member.service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SMSService {

    @Value("${coolsms.api.key}")
    private String apiKey; // 발급받은 apiKey 번호

    @Value("${coolsms.api.secret}")
    private String apiSecret; // 발급받은 apiSecret 번호

    @Value("${coolsms.api.fromnumber}")
    private String fromNumber; // 발신 번호

    public int certifiedPhoneNumber(String toNumber) {
        String api_key = apiKey; // API KEY 값
        String api_secret = apiSecret; // API Secret 값
        Message coolsms = new Message(api_key, api_secret);

        int randomNumber = (int) ((Math.random() * (9999 - 1000 + 1)) + 1000);// 난수 생성

        HashMap<String, String> params = new HashMap<>();
        params.put("to",toNumber ); // 수신 전화번호
        params.put("from", fromNumber); // 발신 전화번호
        params.put("type", "SMS");
        params.put("text", "[리뷰오브레전드] 인증번호 "+randomNumber+" 를 입력하세요.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        return randomNumber;
    }
}