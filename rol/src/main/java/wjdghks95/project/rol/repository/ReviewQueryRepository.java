package wjdghks95.project.rol.repository;

import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wjdghks95.project.rol.domain.entity.Review;

import java.util.List;

public interface ReviewQueryRepository {
    Page<Review> findReviewList(Pageable pageable, Long id, String keyword);

    List<Review> findAllByOrder(OrderSpecifier<?> orderSpecifier);
}
