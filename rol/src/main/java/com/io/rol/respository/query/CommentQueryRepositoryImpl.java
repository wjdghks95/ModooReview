package com.io.rol.respository.query;

import com.io.rol.comment.domain.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.io.rol.domain.entity.QComment.*;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {
    private final JPAQueryFactory queryFactory;

    // id에 해당하는 게시글의 댓글 역순 정렬
    public List<Comment> findAllByIdOrderByCreatedDate(Long id) {
        return queryFactory.selectFrom(comment)
                .where(comment.board.id.eq(id))
                .orderBy(comment.createdDate.desc())
                .fetch();
    }
}
