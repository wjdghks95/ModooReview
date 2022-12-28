package wjdghks95.project.rol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjdghks95.project.rol.domain.dto.ReviewDto;
import wjdghks95.project.rol.domain.entity.*;
import wjdghks95.project.rol.repository.CategoryRepository;
import wjdghks95.project.rol.repository.LikeEntityRepository;
import wjdghks95.project.rol.repository.ReviewRepository;
import wjdghks95.project.rol.repository.ReviewTagRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    private final TagService tagService;
    private final ReviewTagRepository reviewTagRepository;
    private final ImageService imageService;
    private final LikeEntityRepository likeEntityRepository;

    @Transactional
    @Override
    public Long write(ReviewDto reviewDto, Member member) throws IOException {
        List<Image> images = imageService.saveImages(reviewDto.getMultipartFiles());
        List<Tag> tagList = tagService.saveTag(reviewDto.getTagNames());

        Category category = categoryRepository.findByCategoryName(CategoryName.valueOf(reviewDto.getCategoryName().toUpperCase())).orElseThrow();

        Review review = Review.builder()
                .title(reviewDto.getTitle())
                .description(reviewDto.getDescription())
                .rating(reviewDto.getRating())
                .category(category)
                .build();

        review.setMember(member);

        review.setThumbnail(images);
        images.stream().forEach(image -> review.setImage(image));

        Review savedReview = reviewRepository.save(review);

        tagList.forEach(tag -> {
            ReviewTag reviewTag = ReviewTag.builder()
                    .review(savedReview)
                    .tag(tag)
                    .build();

            reviewTagRepository.save(reviewTag);
        });

        return savedReview.getId();
    }


    @Transactional
    @Override
    public void like(Member member, Review review) {
        Optional<LikeEntity> byMemberAndReview = likeEntityRepository.findByMemberAndReview(member, review);
        byMemberAndReview.ifPresentOrElse(
                // 좋아요가 있을 경우 삭제
                likeEntity -> {
                    review.discountLike(likeEntity);
                    review.updateLikeCount();
                    likeEntityRepository.delete(likeEntity);
                },

                // 좋아요가 없을 경우 좋아요 추가
                () -> {
                    LikeEntity likeEntity = LikeEntity.builder()
                                    .review(review)
                                    .member(member)
                                    .build();

                    likeEntity.setReview(review);
                    likeEntity.setMember(member);
                    review.updateLikeCount();
                    likeEntityRepository.save(likeEntity);
                }
        );
    }

    // 로그인한 member와 현재 review를 가진 좋아요가 없을 경우 false
    @Override
    public boolean isLike(Member member, Review review) {
        return likeEntityRepository.findByMemberAndReview(member, review).isEmpty();
    }

    @Override
    @Transactional
    public void visit(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        review.updateVisit();
    }

    @Override
    @Transactional
    public Long edit(Long id, ReviewDto reviewDto) throws IOException {
        Review review = reviewRepository.findById(id).orElseThrow();
        Category category = categoryRepository.findByCategoryName(CategoryName.valueOf(reviewDto.getCategoryName().toUpperCase())).orElseThrow();
        List<Tag> tagList = tagService.saveTag(reviewDto.getTagNames());

        if (!reviewDto.getMultipartFiles().get(0).isEmpty()) {
            imageService.deleteImages(review.getImages());
            review.getImages().clear();

            List<Image> images = imageService.saveImages(reviewDto.getMultipartFiles());
            review.setThumbnail(images);
            images.forEach(image -> review.setImage(image));
        }

        review.setTitle(reviewDto.getTitle());
        review.setCategory(category);
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());

        review.getReviewTag().clear();
        tagList.forEach(tag -> {
            ReviewTag reviewTag = ReviewTag.builder()
                    .review(review)
                    .tag(tag)
                    .build();

            reviewTagRepository.save(reviewTag);
        });

        review.setLastModifiedDate(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        return savedReview.getId();
    }
}
