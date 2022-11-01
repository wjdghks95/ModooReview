package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.repository.MemberRepository;
import wjdghks95.project.rol.security.service.MemberContext;
import wjdghks95.project.rol.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    @GetMapping("/review/{id}/follow")
    public String follow(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext) {
        Member followingMember = memberRepository.findById(memberContext.getMember().getId()).orElseThrow();
        Member followerMember = reviewService.findById(id).getMember();

        reviewService.follow(followingMember, followerMember);

        return "";
    }
}
