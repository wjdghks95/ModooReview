package wjdghks95.project.rol.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wjdghks95.project.rol.domain.entity.Review;

public interface ReviewQueryRepository {
    Page<Review> findReviewList(Pageable pageable, Long id, String keyword);
}
