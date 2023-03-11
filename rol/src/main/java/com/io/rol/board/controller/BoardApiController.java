package com.io.rol.board.controller;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.board.service.BoardService;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.member.service.MemberService;
import com.io.rol.security.context.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardApiController {
    private final MemberService memberService;
    private final BoardService boardService;

    // 좋아요
    @GetMapping("/like")
    public int like(@AuthenticationPrincipal MemberContext memberContext, @RequestParam Long id) {
        Member loginMember = memberService.getMember(memberContext.getMember().getId());
        Board board = boardService.getBoard(id);
        boardService.like(loginMember, board);

        return board.getLikeCount();
    }
}
