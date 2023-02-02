package com.io.rol.controller;

import com.io.rol.domain.dto.NicknameDto;
import com.io.rol.domain.entity.*;
import com.io.rol.security.context.MemberContext;
import com.io.rol.service.ImageService;
import com.io.rol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

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

    @GetMapping("/{id}/following")
    public String following(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberService.findMember(id);

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        List<Follow> followingList = member.getFollowerList();
        List<Member> followingMembers = new ArrayList<>();
        for (Follow follow : followingList) {
            followingMembers.add(follow.getFollowing());
        }

        model.addAttribute("member", member);
        model.addAttribute("followingMembers", followingMembers);

        return "myPage/myPage_following";
    }

    @GetMapping("/{id}/withdrawal")
    public String withdrawalForm(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberService.findMember(id);

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        model.addAttribute("member", member);

        return "myPage/myPage_withdrawal";
    }

    @PostMapping("/{id}/withdrawal")
    @ResponseBody
    public boolean withdrawal(@PathVariable Long id, @RequestParam String pwd) {
        Member member = memberService.findMember(id);
        if (passwordEncoder.matches(pwd, member.getPassword())) {
            memberService.withdrawal(member);
            SecurityContextHolder.clearContext();
            return true;
        }

        return false;
    }
}
