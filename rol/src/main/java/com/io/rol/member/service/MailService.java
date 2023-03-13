package com.io.rol.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

// 메일 서비스
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "<reviewOfLegend@rol.io.com>";

    // 비밀번호 찾기 메일 전송
    public String findPassword(String email) {
        String tempPwd = "";
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
            mailHelper.setTo(email);
            mailHelper.setFrom(FROM_ADDRESS);
            mailHelper.setSubject("[리뷰오브레전드] 비밀번호 찾기 관련 메일 발송");

            tempPwd = getTmpPassword();
            mailHelper.setText("<h2>임시 비밀번호: " + tempPwd +"</h2>", true);

            mailSender.send(mail);
            return tempPwd;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // 임시 비밀번호 발급
    private String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        return pwd;
    }
}
