package wjdghks95.project.rol.controller;

import com.querydsl.core.types.OrderSpecifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wjdghks95.project.rol.domain.entity.QReview;
import wjdghks95.project.rol.domain.entity.Review;
import wjdghks95.project.rol.repository.ReviewQueryRepository;

import java.time.LocalDateTime;
import java.util.List;

import static wjdghks95.project.rol.domain.entity.QReview.review;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ReviewQueryRepository reviewQueryRepository;

    @GetMapping("/")
    public String home(Model model) {
        OrderSpecifier<String> createdDate = review.createdDate.desc();
        OrderSpecifier<Integer> likeCount = review.likeCount.desc();
        OrderSpecifier<Integer> countVisit = review.countVisit.desc();

        List<Review> orderByCreatedDate = reviewQueryRepository.findAllByOrder(createdDate);
        List<Review> orderByLikeCount = reviewQueryRepository.findAllByOrder(likeCount);
        List<Review> orderByCountVisit = reviewQueryRepository.findAllByOrder(countVisit);

        model.addAttribute("orderByLikeCount", orderByLikeCount);
        model.addAttribute("orderByCreatedDate", orderByCreatedDate);
        model.addAttribute("orderByCountVisit", orderByCountVisit);

        return "index";
    }

    @GetMapping("/search/contents")
    public String searchContent(@RequestParam String keyword, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("keyword", keyword);
        return "redirect:/contents";
    }
}