package com.io.rol.comment.repository;

import com.io.rol.comment.domain.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.io.rol.domain.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {
    private final JPAQueryFactory queryFactory;

    // 해당 게시글의 댓글 역순 정렬
    public List<Comment> findCommentListByBoardId(Long id) {
        return queryFactory.selectFrom(comment)
                .where(comment.board.id.eq(id))
                .orderBy(comment.createdDate.desc())
                .fetch();
    }
}
