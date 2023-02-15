package com.io.rol.controller;

import com.io.rol.domain.dto.FindIdDto;
import com.io.rol.domain.dto.MemberDto;
import com.io.rol.domain.entity.Member;
import com.io.rol.service.MemberService;
import com.io.rol.validator.MemberDuplicateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberDuplicateValidator memberDuplicateValidator;

    @InitBinder("memberDto")
    public void memberValidation(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.addValidators(memberDuplicateValidator); // 회원 중복 검사
    }

    /**
     * 회원가입
     */
    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUp";
        }

        memberService.join(memberDto);
        return "redirect:/login";
    }

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String exception, Model model) {

        boolean isError = Boolean.parseBoolean(error);
        if (isError) {
            model.addAttribute("errorMsg", exception);
        }
        return "login";
    }

    /**
     * 아이디 찾기
     */
    @GetMapping("/find/id")
    public String findIdForm(Model model) {
        model.addAttribute("findIdDto", new FindIdDto());
        return "find-id";
    }

    @PostMapping("/find/id")
    public String findId(@Validated @ModelAttribute FindIdDto findIdDto, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "find-id";
        }

        Member member = memberService.findId(findIdDto);
        if (member != null) {
            String email = member.getEmail();
            redirectAttributes.addFlashAttribute("email", email);
        } else {
            redirectAttributes.addFlashAttribute("email", "일치하는 아이디가 없습니다");
        }

        return "redirect:/find/id/result";
    }

    @GetMapping("/find/id/result")
    public String findIdResult(HttpServletRequest request, Model model) {
        String email = null;
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            email = (String) inputFlashMap.get("email");
        }
        model.addAttribute("email", email);
        return "find-id-result";
    }
}
