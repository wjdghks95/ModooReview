package com.io.rol.config;

import com.io.rol.domain.dto.MemberDto;
import com.io.rol.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class InitDB {

    @Autowired MemberService memberService;

    @PostConstruct
    public void saveMember() {
        MemberDto memberDto = MemberDto.builder()
                .phone("01012345678")
                .email("test@test.com")
                .password("asdf1234!")
                .name("이름")
                .nickname("닉네임")
                .zipcode("12345")
                .address("경기도 고양시 일산서구 현중로 10")
                .detailAddress("101동 101호")
                .build();

        memberService.join(memberDto);
    }
}
