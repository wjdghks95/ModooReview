package wjdghks95.project.rol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wjdghks95.project.rol.domain.dto.PageDto;
import wjdghks95.project.rol.domain.entity.Category;
import wjdghks95.project.rol.domain.entity.CategoryName;
import wjdghks95.project.rol.domain.entity.Review;
import wjdghks95.project.rol.repository.CategoryRepository;
import wjdghks95.project.rol.repository.ReviewQueryRepository;

@Controller
@RequiredArgsConstructor
public class ContentsController {
    private final ReviewQueryRepository reviewQueryRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/contents")
    public String contents(@PageableDefault(size = 12) Pageable pageable, Model model) {

        Page<Review> reviewList = reviewQueryRepository.findReviewList(pageable, null);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("page", new PageDto(reviewList.getTotalElements(), pageable));

        return "/contents/contents";
    }

    @GetMapping("/api/contents")
    public String contents(@RequestParam(value = "category") String categoryVal, Model model,
                           @PageableDefault(size = 12) Pageable pageable) {

        Page<Review> reviewList = reviewQueryRepository.findReviewList(pageable, null);

        if (!categoryVal.equals("all")) {
            Category category = categoryRepository.findByCategoryName(CategoryName.valueOf(categoryVal.toUpperCase())).orElseThrow();
            reviewList = reviewQueryRepository.findReviewList(pageable, category.getId());
        }

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("page", new PageDto(reviewList.getTotalElements(), pageable));

        return "/contents/res_contents";
    }
}
