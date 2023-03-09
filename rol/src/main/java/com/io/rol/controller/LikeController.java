package com.io.rol.controller;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.security.context.MemberContext;
import com.io.rol.board.service.BoardService;
import com.io.rol.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 좋아요 컨트롤러
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final MemberService memberService;
    private final BoardService boardService;

    // 좋아요
    @GetMapping("/like.do")
    public int like(@AuthenticationPrincipal MemberContext memberContext, @RequestParam Long id) {
        Member loginMember = memberService.findMember(memberContext.getMember().getId());
        Board board = boardService.findBoard(id);
        boardService.like(loginMember, board);

        return board.getLikeCount();
    }
}
