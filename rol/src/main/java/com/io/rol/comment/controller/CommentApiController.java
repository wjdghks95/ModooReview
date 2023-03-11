package com.io.rol.comment.controller;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.board.service.BoardService;
import com.io.rol.comment.domain.entity.Comment;
import com.io.rol.comment.service.CommentService;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.service.MemberService;
import com.io.rol.security.context.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentApiController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final BoardService boardService;

    // 댓글 작성
    @PostMapping("/comment")
    public String addComment(@AuthenticationPrincipal MemberContext memberContext, @RequestParam String content, @RequestParam Long id,
                             Model model) {
        Board board = boardService.getBoard(id);
        Member loginMember = memberService.getMember(memberContext.getMember().getId());
        commentService.save(board, loginMember, content);
        List<Comment> comments = commentService.getCommentList(id);

        model.addAttribute("comments", comments);
        model.addAttribute("loginMember", loginMember);

        return "/contents/board :: #commentList";
    }

    // 댓글 삭제
    @DeleteMapping("/comment")
    public String delComment(@AuthenticationPrincipal MemberContext memberContext, @RequestParam Long index, @RequestParam Long id,
                             Model model) {
        commentService.remove(index);

        Member loginMember = memberService.getMember(memberContext.getMember().getId());
        List<Comment> comments = commentService.getCommentList(id);

        model.addAttribute("comments", comments);
        model.addAttribute("loginMember", loginMember);

        return "/contents/board :: #commentList";
    }
}
