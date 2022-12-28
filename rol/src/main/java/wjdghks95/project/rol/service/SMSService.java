package wjdghks95.project.rol.service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SMSService {

    @Value("${coolsms.devHee.apikey}")
    private String apiKey;

    @Value("${coolsms.devHee.apisecret}")
    private String apiSecret;

    @Value("${coolsms.devHee.fromnumber}")
    private String fromNumber;

    public void certifiedPhoneNumber(String toNumber, int randomNumber) {
        String api_key = apiKey;
        String api_secret = apiSecret;
        Message coolsms = new Message(api_key, api_secret);

        HashMap<String, String> params = new HashMap<>();
        params.put("to",toNumber );
        params.put("from", fromNumber);
        params.put("type", "SMS");
        params.put("text", "[리뷰오브레전드] 인증번호 "+randomNumber+" 를 입력하세요.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
