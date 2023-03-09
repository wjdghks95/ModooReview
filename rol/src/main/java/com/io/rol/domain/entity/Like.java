package com.io.rol.domain.entity;

import com.io.rol.board.domain.entity.Board;
import com.io.rol.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 좋아요 Entity
@Table(name = "likes")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void setMember(Member member) {
        this.member = member;
        member.getLikeList().add(this);
    }

    public void setReview(Board board) {
        this.board = board;
        board.getLikeList().add(this);
    }
}
