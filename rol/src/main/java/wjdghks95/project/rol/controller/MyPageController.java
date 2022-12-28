package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wjdghks95.project.rol.domain.dto.MemberWithdrawalDto;
import wjdghks95.project.rol.domain.dto.NicknameDto;
import wjdghks95.project.rol.domain.entity.Follow;
import wjdghks95.project.rol.domain.entity.LikeEntity;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.domain.entity.Review;
import wjdghks95.project.rol.repository.MemberRepository;
import wjdghks95.project.rol.security.service.MemberContext;
import wjdghks95.project.rol.service.MemberService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberRepository.findById(id).orElseThrow();

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        model.addAttribute("member", member);
        model.addAttribute("nicknameDto", new NicknameDto());
        return "myPage/myPage_profile";
    }

    @PostMapping("/profile/{id}/nickname")
    public String updateNickname(@PathVariable Long id, @ModelAttribute @Validated NicknameDto nicknameDto,
                                BindingResult bindingResult, Model model) {
        Member member = memberRepository.findById(id).orElseThrow();

        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            return "myPage/myPage_profile";
        }

        member.setNickname(nicknameDto.getNickname());
        memberRepository.save(member);

        return "redirect:/myPage/profile/" + id;
    }

    @PostMapping("/profile/{id}/profileImg")
    public String updateProfileImg(@PathVariable Long id, MultipartFile multipartFile) throws IOException {
        Member member = memberRepository.findById(id).orElseThrow();
        memberService.updateProfileImg(member, multipartFile);

        return "redirect:/myPage/profile/" + id;
    }

    @GetMapping("/myReview/{id}")
    public String myReview(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberRepository.findById(id).orElseThrow();

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        List<Review> reviewList = member.getReviewList();

        model.addAttribute("reviewList", reviewList);
        return "myPage/myPage_myReview";
    }

    @GetMapping("/like/{id}")
    public String myLike(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberRepository.findById(id).orElseThrow();

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        List<LikeEntity> likeList = member.getLikeList();

        model.addAttribute("likeList", likeList);
        return "myPage/myPage_like";
    }

    @GetMapping("/following/{id}")
    public String following(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberRepository.findById(id).orElseThrow();

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        List<Follow> followingList = member.getFollowingList();
        model.addAttribute("followingList", followingList);

        return "myPage/myPage_following";
    }

    @GetMapping("/withdrawal/{id}")
    public String withdrawalForm(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberRepository.findById(id).orElseThrow();

        if (member.getId() != memberContext.getMember().getId()) {
            return "redirect:/";
        }

        model.addAttribute("member", member);
        model.addAttribute("memberWithdrawalDto", new MemberWithdrawalDto());

        return "myPage/myPage_withdrawal";
    }

    @PostMapping("/withdrawal/{id}")
    public String withdrawal(@PathVariable Long id, @ModelAttribute MemberWithdrawalDto memberWithdrawalDto) {
        if (memberWithdrawalDto.isCheck1() && memberWithdrawalDto.isCheck2()) {
            memberService.withdrawal(id);
            SecurityContextHolder.clearContext();
            return "redirect:/";
        }

        return "redirect:/myPage/profile/" + id;
    }
}
