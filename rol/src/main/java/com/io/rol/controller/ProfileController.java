package com.io.rol.controller;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.domain.entity.Like;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.security.context.MemberContext;
import com.io.rol.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

// 프로필 컨트롤러
@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final MemberService memberService;

    // 프로필
    @GetMapping("/{id}")
    public String portfolio(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Member member = memberService.findMember(id);
        Member loginMember = memberContext != null ? memberContext.getMember() : null;

        List<Board> boardList = member.getBoardList();
        Collections.reverse(boardList);

        List<Like> likeList = member.getLikeList();
        Collections.reverse(likeList);

        boolean isFollow = memberService.isFollow(loginMember == null ? null : loginMember.getId(), member.getId());

        model.addAttribute("member", member);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("boardList", boardList);
        model.addAttribute("likeList", likeList);
        model.addAttribute("isFollow", isFollow);

        return "profile/profile";
    }

    // 프로필 작성 게시글 목록
    @GetMapping("/{id}/boardList")
    public String reviewList(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Member member = memberService.findMember(id);
        Member loginMember = memberContext != null ? memberContext.getMember() : null;

        List<Board> boardList = member.getBoardList();
        Collections.reverse(boardList);

        boolean isFollow = memberService.isFollow(loginMember == null ? null : loginMember.getId(), member.getId());

        model.addAttribute("member", member);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("boardList", boardList);
        model.addAttribute("isFollow", isFollow);

        return "profile/profile_review";
    }

    // 프로필 좋아요한 게시글 목록
    @GetMapping("/{id}/likeList")
    public String likeList(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Member member = memberService.findMember(id);
        Member loginMember = memberContext != null ? memberContext.getMember() : null;

        List<Like> likeList = member.getLikeList();
        Collections.reverse(likeList);

        boolean isFollow = memberService.isFollow(loginMember == null ? null : loginMember.getId(), member.getId());

        model.addAttribute("member", member);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("likeList", likeList);
        model.addAttribute("isFollow", isFollow);

        return "profile/profile_like";
    }
}
