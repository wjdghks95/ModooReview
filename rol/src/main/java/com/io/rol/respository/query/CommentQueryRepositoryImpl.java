package com.io.rol.respository.query;

import com.io.rol.domain.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.io.rol.domain.entity.QComment.*;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Comment> findAllByIdOrderByCreatedDate(Long id) {
        return queryFactory.selectFrom(comment)
                .where(comment.board.id.eq(id))
                .orderBy(comment.createdDate.desc())
                .fetch();
    }
}
