package com.io.rol.controller;

import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Member;
import com.io.rol.service.BoardService;
import com.io.rol.service.MemberService;
import com.io.rol.validator.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final FileValidator fileValidator;

    @InitBinder("boardDto")
    public void boardValidation(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.addValidators(fileValidator); // 이미지 파일 업로드 여부 검사
    }

    /**
     * 게시글 등록
     */
    @GetMapping("/board/new")
    public String boardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "contents/boardForm";
    }

    @PostMapping("/board/new")
    public String newBoard(@Validated @ModelAttribute BoardDto boardDto, BindingResult bindingResult,
                           @AuthenticationPrincipal Member member) throws IOException {

        if (bindingResult.hasErrors()) {
            return "contents/boardForm";
        }

        Member writer = memberService.findMember(member.getId());
        boardService.write(boardDto, writer);
        return "redirect:/";
    }
}
