package com.io.rol.controller;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.domain.entity.Comment;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.security.context.MemberContext;
import com.io.rol.board.service.BoardService;
import com.io.rol.service.CommentService;
import com.io.rol.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 댓글 컨트롤러
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final BoardService boardService;

    // 댓글 작성
    @PostMapping("/comment.do")
    public String addComment(@AuthenticationPrincipal MemberContext memberContext, @RequestParam String content, @RequestParam Long id,
                          Model model) {
        Board board = boardService.getBoard(id);
        Member loginMember = memberService.getMember(memberContext.getMember().getId());
        commentService.insert(board, loginMember, content);
        List<Comment> comments = commentService.getList(id);

        model.addAttribute("comments", comments);
        model.addAttribute("loginMember", loginMember);

        return "/contents/board :: #commentList";
    }

    // 댓글 삭제
    @GetMapping("/comment.de")
    public String delComment(@AuthenticationPrincipal MemberContext memberContext, @RequestParam Long index, @RequestParam Long id,
                             Model model) {
        commentService.remove(index);

        Member loginMember = memberService.getMember(memberContext.getMember().getId());
        List<Comment> comments = commentService.getList(id);

        model.addAttribute("comments", comments);
        model.addAttribute("loginMember", loginMember);

        return "/contents/board :: #commentList";
    }
}
