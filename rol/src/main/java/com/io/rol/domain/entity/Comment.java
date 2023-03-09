package com.io.rol.domain.entity;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.common.auditing.BaseTimeEntity;
import com.io.rol.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 댓글 Entity
@Entity(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id; // primary key

    @Column(nullable = false)
    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; // 게시글

    @Builder
    public Comment(String content) {
        this.content = content;
    }

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }
}