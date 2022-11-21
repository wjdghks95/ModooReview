package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wjdghks95.project.rol.domain.entity.LikeEntity;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.domain.entity.Review;
import wjdghks95.project.rol.repository.MemberRepository;
import wjdghks95.project.rol.security.service.MemberContext;
import wjdghks95.project.rol.service.MemberService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/{id}")
    public String portfolio(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Member member = memberRepository.findById(id).orElseThrow();
        Member loginMember = memberContext != null ? memberContext.getMember() : null;

        List<Review> reviewList = member.getReviewList();
        Collections.reverse(reviewList);

        List<LikeEntity> likeList = member.getLikeList();
        Collections.reverse(likeList);

        boolean isFollow = memberService.isFollow(loginMember, member);

        model.addAttribute("member", member);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("likeList", likeList);
        model.addAttribute("isFollow", isFollow);

        return "portfolio/portfolio";
    }
}
