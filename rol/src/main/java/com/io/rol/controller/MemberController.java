package com.io.rol.controller;

import com.io.rol.domain.dto.MemberDto;
import com.io.rol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signUp")
    public String signUpForm() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute MemberDto memberDto) {
        memberService.join(memberDto);
        return "redirect:/login";
    }
}
