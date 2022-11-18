package wjdghks95.project.rol.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import wjdghks95.project.rol.domain.entity.ReviewTag;
import wjdghks95.project.rol.domain.entity.Tag;

import java.util.List;

import static wjdghks95.project.rol.domain.entity.QReview.review;
import static wjdghks95.project.rol.domain.entity.QReviewTag.reviewTag;

@Repository
@RequiredArgsConstructor
public class ReviewTagQueryRepositoryImpl implements ReviewTagQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewTag> findReviewTagList(Pageable pageable, Tag tag) {
        List<ReviewTag> reviewList = queryFactory.selectFrom(reviewTag)
                .where(tagCon(tag))
                .orderBy(reviewTag.review.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = getCount(tag);

        return PageableExecutionUtils.getPage(reviewList, pageable, () -> count.fetchOne());
    }

    private JPAQuery<Long> getCount(Tag tag) {
        return queryFactory
                .select(reviewTag.count())
                .from(reviewTag)
                .where(tagCon(tag));
    }

    private BooleanExpression tagCon(Tag tag) {
        return tag != null ? reviewTag.tag.eq(tag) : null;
    }
}
