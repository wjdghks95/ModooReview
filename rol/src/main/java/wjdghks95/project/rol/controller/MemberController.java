package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import wjdghks95.project.rol.domain.dto.MemberDto;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.service.MemberService;
import wjdghks95.project.rol.validator.MemberDuplicateValidator;
import wjdghks95.project.rol.validator.AuthCheckValidator;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberDuplicateValidator memberDuplicateValidator;

    private final AuthCheckValidator authCheckValidator;

    @InitBinder("memberDto")
    public void memberDuplicateValidation(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.addValidators(memberDuplicateValidator);
        dataBinder.addValidators(authCheckValidator);
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("errorMessage", exception);
        return "loginForm";
    }

    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "signUpForm";
    }

    @PostMapping("/signUp")
    public String signUp(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signUpForm";
        }

        memberService.join(memberDto);

        return "redirect:/";
    }
}
