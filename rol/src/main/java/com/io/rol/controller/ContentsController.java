package com.io.rol.controller;

import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Member;
import com.io.rol.service.BoardService;
import com.io.rol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final BoardService boardService;
    private final MemberService memberService;

    /**
     * 게시글 등록
     */
    @GetMapping("/board/new")
    public String boardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "contents/boardForm";
    }

    @PostMapping("/board/new")
    public String newBoard(@ModelAttribute BoardDto boardDto, @AuthenticationPrincipal Member member) throws IOException {
        Member writer = memberService.findMember(member.getId());
        boardService.write(boardDto, writer);
        return "redirect:/";
    }
}
