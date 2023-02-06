package com.io.rol.controller;

import com.io.rol.domain.entity.Member;
import com.io.rol.security.context.MemberContext;
import com.io.rol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final MemberService memberService;

    /**
     * 팔로우
     */
    @GetMapping("/follow.do")
    @ResponseBody
    public int follow(@AuthenticationPrincipal MemberContext memberContext, @RequestParam Long id) {
        Member loginMember = memberService.findMember(memberContext.getMember().getId());
        Member writer = memberService.findMember(id);

        memberService.follow(loginMember, writer);
        int size = writer.getFollowingList().size();
        return size;
    }
}
