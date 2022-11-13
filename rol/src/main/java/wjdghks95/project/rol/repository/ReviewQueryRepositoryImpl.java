package wjdghks95.project.rol.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import wjdghks95.project.rol.domain.entity.QReview;
import wjdghks95.project.rol.domain.entity.Review;

import java.util.List;

import static wjdghks95.project.rol.domain.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> findReviewList(Pageable pageable, Long id, String keyword) {
        List<Review> reviewList = queryFactory.selectFrom(review)
                .where(categoryCon(id), titleCon(keyword))
                .orderBy(review.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = getCount(id, keyword);

        return PageableExecutionUtils.getPage(reviewList, pageable, () -> count.fetchOne());
    }

    @Override
    public List<Review> findAllByOrder(OrderSpecifier<?> orderSpecifier) {
        return queryFactory.selectFrom(review)
                .orderBy(orderSpecifier)
                .fetch();
    }

    private JPAQuery<Long> getCount(Long id, String keyword) {
        return queryFactory
                .select(review.count())
                .from(review)
                .where(categoryCon(id), titleCon(keyword));
    }

    private BooleanExpression categoryCon(Long id) {
        return id != null ? review.category.id.eq(id) : null;
    }

    private BooleanExpression titleCon(String keyword) {
        return keyword != null ? review.title.contains(keyword) : null;
    }
}
