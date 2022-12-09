package com.io.rol.domain.entity;

import com.io.rol.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id; // primary key

    /* 게시글이 삭제되면 게시글에 사용된 이미지 모두 삭제 */
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>(); // 이미지 목록

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail; // 썸네일

    @Column(nullable = false, length = 100)
    private String title; // 제목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리

    @Lob
    @Column(nullable = false)
    private String description; // 설명

    private int rating; // 별점

    private int views; // 조회수

    private int likeCount; // 좋아요수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원

    @OneToMany(mappedBy = "board")
    private List<BoardTag> boardTagList = new ArrayList<>(); // boardTag 목록

    @Builder
    public Board(String title, Category category, String description, int rating) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.rating = rating;
        this.views = 0;
        this.likeCount = 0;
    }

    /** 연관관계 편의 메서드 */
    public void setMember(Member member) {
        this.member = member;
        member.getBoardList().add(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getBoardList().add(this);
    }

    /**
     * 썸네일 등록
     */
    public void setThumbnail(List<Image> images, int thumbnailIdx) {
        this.thumbnail = images.get(thumbnailIdx);
    }
}
