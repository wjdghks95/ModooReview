package com.io.rol.respository.query;

import com.io.rol.domain.entity.Board;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.io.rol.domain.entity.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Board> findAllPagingByKeyword(Pageable pageable, String category, String keyword) {
        List<Board> boardList = queryFactory
                .selectFrom(board)
                .join(board.category).fetchJoin()
                .join(board.member).fetchJoin()
                .join(board.images).fetchJoin()
                .where(categoryCon(category), titleCon(keyword))
                .orderBy(board.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = getCount(category, keyword);

        return PageableExecutionUtils.getPage(boardList, pageable, () -> count.fetchOne());
    }

    @Override
    public List<Board> findAllByOrder(OrderSpecifier<?> orderSpecifier) {
        return queryFactory.selectFrom(board)
                .orderBy(orderSpecifier)
                .fetch();
    }

    private JPAQuery<Long> getCount(String category, String keyword) {
        return queryFactory
                .select(board.count())
                .from(board)
                .where(categoryCon(category), titleCon(keyword));
    }
    private BooleanExpression categoryCon(String category) {
        return category == null || category.equals("") ? null : board.category.name.eq(category);
    }

    private BooleanExpression titleCon(String keyword) {
        return keyword == null || keyword.equals("") ? null : board.title.contains(keyword);
    }
}
