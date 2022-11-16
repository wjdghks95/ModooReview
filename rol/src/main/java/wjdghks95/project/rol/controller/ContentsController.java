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
import wjdghks95.project.rol.domain.entity.*;
import wjdghks95.project.rol.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ContentsController {
    private final ReviewQueryRepository reviewQueryRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ReviewTagQueryRepository reviewTagQueryRepository;

    @GetMapping("/contents")
    public String contents(@PageableDefault(size = 12) Pageable pageable, Model model,
                           @RequestParam(required = false, defaultValue = "") String keyword) {

        Page<Review> reviewList = reviewQueryRepository.findReviewList(pageable, null, keyword);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("page", new PageDto(reviewList.getTotalElements(), pageable));

        return "/contents/contents";
    }

    @GetMapping("/api/contents")
    public String contents(@RequestParam(value = "category", defaultValue = "all") String categoryVal, Model model,
                           @PageableDefault(size = 12) Pageable pageable, @RequestParam(required = false, defaultValue = "") String keyword) {

        Page<Review> reviewList = reviewQueryRepository.findReviewList(pageable, null, keyword);

        if (!categoryVal.equals("all")) {
            Category category = categoryRepository.findByCategoryName(CategoryName.valueOf(categoryVal.toUpperCase())).orElseThrow();
            reviewList = reviewQueryRepository.findReviewList(pageable, category.getId(), keyword);
        }

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("page", new PageDto(reviewList.getTotalElements(), pageable));

        return "/contents/res_contents";
    }

    @GetMapping("/contents/hashTag")
    public String hashTag(@RequestParam String tagName, Model model, @PageableDefault(size = 12) Pageable pageable) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow();
        Page<ReviewTag> reviewTagList = reviewTagQueryRepository.findReviewTagList(pageable, tag);
        List<Review> reviewList = reviewTagList.stream().map(reviewTag -> reviewTag.getReview()).collect(Collectors.toList());

        model.addAttribute("totalElement", reviewTagList.getTotalElements());
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("tagName", tagName);

        return "/contents/hashTag";
    }

    @GetMapping("/api/contents/hashTag")
    public String scrollPaging(@RequestParam String tagName, Model model, @PageableDefault(size = 12) Pageable pageable) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow();
        Page<ReviewTag> reviewTagList = reviewTagQueryRepository.findReviewTagList(pageable, tag);
        List<Review> reviewList = reviewTagList.stream().map(reviewTag -> reviewTag.getReview()).collect(Collectors.toList());

        model.addAttribute("reviewList", reviewList);

        return "/contents/res_hashTag";
    }
}
