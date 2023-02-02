package com.io.rol.controller;

import com.io.rol.domain.dto.NicknameDto;
import com.io.rol.domain.entity.Board;
import com.io.rol.domain.entity.Image;
import com.io.rol.domain.entity.Like;
import com.io.rol.domain.entity.Member;
import com.io.rol.security.context.MemberContext;
import com.io.rol.service.ImageService;
import com.io.rol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {

    private final MemberService memberService;
    private final ImageService imageService;

    @GetMapping("/{id}/profile")
    public String profile(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberService.findMember(id);

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        model.addAttribute("member", member);
        return "myPage/myPage_profile";
    }

    @PostMapping("/{id}/profile/profileImg")
    public String updateProfileImg(@PathVariable Long id, MultipartFile multipartFile) throws IOException {
        Member member = memberService.findMember(id);
        Image image = imageService.saveImage(multipartFile);
        memberService.profileImgModify(member, image);

        return "redirect:/myPage/profile/" + id;
    }

    @PostMapping("/{id}/profile/nickname")
    public String updateNickname(@PathVariable Long id, @ModelAttribute @Validated NicknameDto nicknameDto,
                                 BindingResult bindingResult, Model model) {
        Member member = memberService.findMember(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            model.addAttribute("error", true);
            model.addAttribute("msg", "올바르지 않은 닉네임입니다.");
            return "myPage/myPage_profile";
        }

        memberService.nicknameModify(member, nicknameDto.getNickname());

        return "redirect:/myPage/profile/" + id;
    }

    @GetMapping("/{id}/myReview")
    public String myReview(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberService.findMember(id);

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        List<Board> boardList = member.getBoardList();

        model.addAttribute("member", member);
        model.addAttribute("boardList", boardList);
        return "myPage/myPage_myReview";
    }

    @GetMapping("/{id}/like")
    public String myLike(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberService.findMember(id);

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        List<Like> likeList = member.getLikeList();
        List<Board> boardList = new ArrayList<>();
        for (Like like : likeList) {
            boardList.add(like.getBoard());
        }

        model.addAttribute("member", member);
        model.addAttribute("boardList", boardList);
        return "myPage/myPage_like";
    }
}
