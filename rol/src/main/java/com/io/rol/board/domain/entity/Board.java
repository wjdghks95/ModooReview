package com.io.rol.board.domain.entity;

import com.io.rol.Image.domain.entity.Image;
import com.io.rol.category.domain.entity.Category;
import com.io.rol.domain.entity.*;
import com.io.rol.member.domain.entity.Member;
import com.io.rol.tag.domain.entity.BoardTag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 리뷰 Entity
@Entity(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id; // primary key

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail; // 썸네일

    @Column(nullable = false, length = 100)
    private String title; // 제목

    @Lob
    @Column(nullable = false)
    private String description; // 설명

    private int rating; // 별점

    private int views; // 조회수

    private int likeCount; // 좋아요수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리

    // 게시글이 삭제되면 게시글에 사용된 이미지 모두 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>(); // 이미지 목록

    // 게시글이 삭제되면 게시글 태그 모두 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardTag> boardTagList = new ArrayList<>(); // boardTag 목록

    // 게시글이 삭제되면 게시글에 작성된 댓글 모두 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 댓글 목록

    // 게시글이 삭제되면 게시글 좋아요 모두 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>(); // 좋아요 목록

    private LocalDateTime createdDate; // 생성일

    private LocalDateTime lastModifiedDate; // 마지막 수정일

    @Builder
    public Board(String title, Category category, String description, int rating) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.rating = rating;
        this.views = 0;
        this.likeCount = 0;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = createdDate;
    }

    //== 연관관계 편의 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getBoardList().add(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getBoardList().add(this);
    }

    // 썸네일 등록
    public void setThumbnail(List<Image> images, int thumbnailIdx) {
        this.thumbnail = images.get(thumbnailIdx);
    }

    // 조회수 증가
    public void incrementViews() {
        ++this.views;
    }

    // 좋아요 수
    public void updateLikeCount() {
        this.likeCount = this.likeList.size();
    }

    // 좋아요 제거
    public void removeLike(Like like) {
        this.likeList.remove(like);
    }

    // 수정
    public void updateBoard(String title, int rating, String description) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.lastModifiedDate = LocalDateTime.now();
    }
}
