package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import wjdghks95.project.rol.domain.dto.CommentDto;
import wjdghks95.project.rol.domain.dto.ReviewDto;
import wjdghks95.project.rol.domain.entity.Comment;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.domain.entity.Review;
import wjdghks95.project.rol.repository.CommentRepository;
import wjdghks95.project.rol.repository.MemberRepository;
import wjdghks95.project.rol.repository.ReviewRepository;
import wjdghks95.project.rol.security.service.MemberContext;
import wjdghks95.project.rol.service.CommentService;
import wjdghks95.project.rol.service.MemberService;
import wjdghks95.project.rol.service.ReviewService;
import wjdghks95.project.rol.validator.FileValidator;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final FileValidator fileValidator;

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @InitBinder("reviewDto")
    public void reviewValidation(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.addValidators(fileValidator);
    }

    @GetMapping("/new")
    public String reviewForm(Model model) {
        model.addAttribute("reviewDto", new ReviewDto());
        return "review/reviewForm";
    }

    @PostMapping("/new")
    public String newReview(@Validated @ModelAttribute ReviewDto reviewDto, BindingResult bindingResult, @AuthenticationPrincipal MemberContext memberContext) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult: {}", bindingResult.getFieldError());
            return "/review/reviewForm";
        }

        Long memberId = memberContext.getMember().getId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("NoSuchElementException"));
        Long reviewId = reviewService.write(reviewDto, member);

        return "redirect:/review/" + reviewId;
    }

    @GetMapping("/{id}")
    public String review(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Review review = reviewRepository.findById(id).orElseThrow();
        reviewService.visit(id);
        model.addAttribute("review", review);

        model.addAttribute("commentDto", new CommentDto());

        List<Comment> comments = commentService.findComments(id);
        model.addAttribute("comments", comments);

        if (memberContext != null) {
            Member member = memberRepository.findById(memberContext.getMember().getId()).orElseThrow();
            model.addAttribute("member", member);

            boolean isLike = reviewService.isLike(member, review);
            model.addAttribute("isLike", isLike);

            boolean isFollow = memberService.isFollow(member, review.getMember());
            model.addAttribute("isFollow", isFollow);
        }

        return "/review/review";
    }

    @PostMapping("/{id}")
    public String addComment(@PathVariable Long id,@Validated @ModelAttribute CommentDto commentDto, BindingResult bindingResult,
                             @AuthenticationPrincipal MemberContext memberContext, Model model) {

        Review review = reviewRepository.findById(id).orElseThrow();
        Member member = memberRepository.findById(memberContext.getMember().getId()).orElseThrow();

        if (bindingResult.hasErrors()) {
            model.addAttribute(review);
            model.addAttribute(member);

            boolean isLike = reviewService.isLike(member, review);
            model.addAttribute("isLike", isLike);

            boolean isFollow = memberService.isFollow(member, review.getMember());
            model.addAttribute("isFollow", isFollow);

            List<Comment> comments = commentService.findComments(id);
            model.addAttribute("comments", comments);

            return "/review/review";
        }

        commentService.add(commentDto, member, review);

        List<Comment> comments = commentService.findComments(id);
        model.addAttribute("comments", comments);

        return "redirect:/review/" + id;
    }

    @GetMapping("/comment/delete/{idx}")
    public String delComment(@PathVariable Long idx, @AuthenticationPrincipal MemberContext memberContext) {
        Comment comment = commentRepository.findById(idx).orElseThrow();
        if (memberContext.getMember().getId() != comment.getMember().getId() || memberContext.getMember() == null) {
            throw new IllegalStateException();
        }
        Long id = comment.getReview().getId();

        commentRepository.delete(comment);

        return "redirect:/review/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {
        Review review = reviewRepository.findById(id).orElseThrow();
        if (memberContext.getMember().getId() != review.getMember().getId() || memberContext.getMember() == null) {
            throw new IllegalStateException();
        }

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTitle(review.getTitle());
        reviewDto.setDescription(review.getDescription());

        model.addAttribute("review", review);
        model.addAttribute("editDto", reviewDto);

        return "/review/reviewEditForm";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Validated @ModelAttribute(name = "editDto") ReviewDto reviewDto,
                       BindingResult bindingResult, Model model) throws IOException {
        Review review = reviewRepository.findById(id).orElseThrow();
        model.addAttribute("review", review);

        if (bindingResult.hasErrors()) {
            log.info("bindingResult: {}", bindingResult.getFieldError());
            return "/review/reviewEditForm";
        }

        Long reviewId = reviewService.edit(id, reviewDto);

        return "redirect:/review/" + reviewId;
    }

    @GetMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext) {
        Review review = reviewRepository.findById(id).orElseThrow();
        if (memberContext.getMember().getId() != review.getMember().getId() || memberContext.getMember() == null) {
            throw new IllegalStateException();
        }
        reviewRepository.delete(review);

        return "redirect:/";
    }
}
