package com.io.rol.member.controller;

import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.service.MemberService;
import com.io.rol.member.service.SMSService;
import com.io.rol.security.context.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {
    private final SMSService smsService;
    private final MemberService memberService;

    /*
       coolsms api를 이용한 SMS 문자 인증
       인증번호 발송 버튼 클릭시 비동기로 전달
     */
    @GetMapping("/SMS")
    public String sendSMS(@RequestParam("phoneNum") String phoneNumber) {
        int randomNumber = smsService.certifiedPhoneNumber(phoneNumber);
        log.info("randomNumber={}", randomNumber);

        return Integer.toString(randomNumber); // data 반환
    }

    // 팔로우
    @GetMapping("/follow")
    @ResponseBody
    public String follow(@AuthenticationPrincipal MemberContext memberContext, @RequestParam Long id) {
        Member loginMember = memberService.getMember(memberContext.getMember().getId());
        Member writer = memberService.getMember(id);

        memberService.follow(loginMember, writer);
        int size = writer.getFollowerList().size();
        return Integer.toString(size);
    }
}
