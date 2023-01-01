package com.io.rol.controller;

import com.io.rol.domain.dto.BoardDto;
import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Comment;
import com.io.rol.domain.entity.Member;
import com.io.rol.security.context.MemberContext;
import com.io.rol.service.BoardService;
import com.io.rol.service.CommentService;
import com.io.rol.service.MemberService;
import com.io.rol.validator.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final FileValidator fileValidator;
    private final CommentService commentService;

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
                           @AuthenticationPrincipal MemberContext memberContext) throws IOException {

        if (bindingResult.hasErrors()) {
            return "contents/boardForm";
        }

        Member writer = memberService.findMember(memberContext.getMember().getId());
        Long id = boardService.write(boardDto, writer);
        return "redirect:/contents/board/" + id;
    }

    /**
     * 게시글
     */
    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Board board = boardService.findBoard(id);
        board.incrementViews(); // 조회수 증가
        model.addAttribute("board", board);

        List<Comment> comments = commentService.getList(id);
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        if (memberContext != null) {
            Long memberContextId = memberContext.getMember().getId();
            Member loginMember = memberService.findMember(memberContextId);
            boolean isFollow = memberService.isFollow(loginMember.getId(), board.getMember().getId());
            boolean isLike = boardService.isLike(loginMember.getId(), board.getId());

            model.addAttribute("loginMember", loginMember);
            model.addAttribute("isFollow", isFollow);
            model.addAttribute("isLike", isLike);
        }

        return "/contents/board";
    }
}
