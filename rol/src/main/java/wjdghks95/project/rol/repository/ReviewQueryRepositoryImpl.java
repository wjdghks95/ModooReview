package wjdghks95.project.rol.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import wjdghks95.project.rol.domain.entity.Review;

import java.util.List;

import static wjdghks95.project.rol.domain.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> findReviewList(Pageable pageable, Long id) {
        List<Review> reviewList = queryFactory.selectFrom(review)
                .where(categoryCon(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = getCount(id);

        return PageableExecutionUtils.getPage(reviewList, pageable, () -> count.fetchOne());
    }

    private JPAQuery<Long> getCount(Long id) {
        return queryFactory
                .select(review.count())
                .from(review)
                .where(categoryCon(id));
    }

    private BooleanExpression categoryCon(Long id) {
        return id != null ? review.category.id.eq(id) : null;
    }
}
