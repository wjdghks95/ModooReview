package com.io.rol.respository.query;

import com.io.rol.tag.domain.entity.BoardTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.io.rol.domain.entity.QBoardTag.*;

@Repository
@RequiredArgsConstructor
public class BoardTagQueryRepositoryImpl implements BoardTagQueryRepository{

    private final JPAQueryFactory queryFactory;

    // 태그 이름으로 페이징 처리 후 조회
    @Override
    public Page<BoardTag> findBoardTagByTagName(Pageable pageable, String tagName) {
        List<BoardTag> boardTagList = queryFactory
                .selectFrom(boardTag)
                .where(tagCon(tagName))
                .orderBy(boardTag.board.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = getCount(tagName);

        return PageableExecutionUtils.getPage(boardTagList, pageable, () -> count.fetchOne());
    }

    private JPAQuery<Long> getCount(String tagName) {
        return queryFactory
                .select(boardTag.count())
                .from(boardTag)
                .where(tagCon(tagName));
    }

    private BooleanExpression tagCon(String tagName) {
        return tagName == null || tagName.equals("") ? null : boardTag.tag.name.eq(tagName);
    }
}
