package wjdghks95.project.rol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjdghks95.project.rol.domain.dto.ReviewDto;
import wjdghks95.project.rol.domain.entity.*;
import wjdghks95.project.rol.repository.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final ReviewTagRepository reviewTagRepository;
    private final ImageService imageService;
    private final LikeEntityRepository likeEntityRepository;
    private final FollowRepository followRepository;

    @Transactional
    @Override
    public Long write(ReviewDto reviewDto, Member member) throws IOException {
        List<Image> images = imageService.saveImages(reviewDto.getMultipartFiles());

        Category category = categoryService.saveCategory(reviewDto.getCategoryName());
        List<Tag> tagList = tagService.saveTag(reviewDto.getTagNames());

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

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow();
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

    @Transactional
    @Override
    public void follow(Member followingMember, Member followerMember) {
        Optional<Follow> byFollow = followRepository.findFollow(followingMember, followerMember);

        byFollow.ifPresentOrElse(
                // 팔로우 되어 있는 경우 삭제
                follow -> {
                    followRepository.delete(follow);
                },
                // 팔로우 하지 않은 경우 추가
                () -> {
                    Follow follow = Follow.builder()
                            .following(followingMember)
                            .follower(followerMember)
                            .build();

                    follow.setFollowing(followingMember);
                    follow.setFollower(followerMember);
                    followRepository.save(follow);
                }
        );
    }

    // 로그인한 member가 현재 page member를 팔로우하지 않은 경우 false
    @Override
    public boolean isFollow(Member followingMember, Member followerMember) {
        return followRepository.findFollow(followingMember, followerMember).isEmpty();
    }


}
