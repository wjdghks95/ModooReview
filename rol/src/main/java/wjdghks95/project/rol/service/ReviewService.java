package wjdghks95.project.rol.service;

import wjdghks95.project.rol.domain.dto.ReviewDto;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.domain.entity.Review;

import java.io.IOException;

public interface ReviewService {
    Long write(ReviewDto reviewDto, Member member) throws IOException;

    void like(Member member, Review review);

    boolean isLike(Member member, Review review);

    void visit(Long id);
    Long edit(Long id, ReviewDto reviewDto) throws IOException;
}


