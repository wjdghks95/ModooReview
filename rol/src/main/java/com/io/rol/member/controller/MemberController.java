package com.io.rol.member.controller;

import com.io.rol.domain.dto.FindIdDto;
import com.io.rol.domain.dto.FindPwdDto;
import com.io.rol.member.domain.dto.MemberDto;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.service.MemberService;
import com.io.rol.service.MailService;
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
    private final MemberDuplicateValidator memberDuplicateValidator; // 회원 중복 검사기
    private final MailService mailService;

    @InitBinder("memberDto")
    public void memberValidation(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.addValidators(memberDuplicateValidator);
    }

    // 회원가입 화면
    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "account/signUp";
    }

    // 회원가입
    @PostMapping("/signUp")
    public String signUp(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // memberDto biding 과정에 오류가 존재하는 경우 오류 메시지와 함께 다시 화면을 띄움
            return "account/signUp";
        }

        memberService.join(memberDto);
        return "redirect:/login";
    }

    // 로그인
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String exception, Model model) {

        boolean isError = Boolean.parseBoolean(error);
        if (isError) {
            model.addAttribute("errorMsg", exception);
        }
        return "login";
    }

    // 아이디 찾기 화면
    @GetMapping("/find/id")
    public String findIdForm(Model model) {
        model.addAttribute("findIdDto", new FindIdDto());
        return "find-id";
    }

    // 아이디 찾기
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

    // 아이디 찾기 결과 화면
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

    // 비밀번호 찾기 화면
    @GetMapping("/find/password")
    public String findPasswordForm(Model model) {
        model.addAttribute("findPwdDto", new FindPwdDto());
        return "find-password";
    }

    // 비밀번호 찾기
    @PostMapping("/find/password")
    public String findPassword(@Validated @ModelAttribute FindPwdDto findPwdDto, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "find-password";
        }

        Member member = memberService.findPassword(findPwdDto);
        if (member != null) {
            String tempPwd = mailService.findPassword(member.getEmail());
            memberService.passwordModify(member, tempPwd);
            redirectAttributes.addFlashAttribute("message", "비밀번호가 이메일로 발송되었습니다");
        } else {
            redirectAttributes.addFlashAttribute("message", "일치하는 아이디가 없습니다");
        }

        return "redirect:/find/password/result";
    }

    // 비밀번호 찾기 결과 화면
    @GetMapping("/find/password/result")
    public String findPasswordResult(HttpServletRequest request, Model model) {
        String message = null;
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            message = (String) inputFlashMap.get("message");
        }
        model.addAttribute("message", message);
        return "find-password-result";
    }
}
