package com.io.rol.controller;

import com.io.rol.domain.dto.NicknameDto;
import com.io.rol.domain.entity.Member;
import com.io.rol.security.context.MemberContext;
import com.io.rol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberService.findMember(id);

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        model.addAttribute("member", member);
        return "myPage/myPage_profile";
    }

    @PostMapping("/profile/{id}/nickname")
    public String updateNickname(@PathVariable Long id, @ModelAttribute @Validated NicknameDto nicknameDto,
                                 BindingResult bindingResult, Model model) {
        Member member = memberService.findMember(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            model.addAttribute("error", true);
            model.addAttribute("msg", "올바르지 않은 닉네임입니다.");
            return "myPage/myPage_profile";
        }

        memberService.nicknameModify(member, nicknameDto.getNickname());

        return "redirect:/myPage/profile/" + id;
    }
}
