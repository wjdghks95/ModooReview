package com.io.rol.domain.entity;

import lombok.*;

import javax.persistence.*;

// 리뷰 게시글 이미지 Entity
@Entity(name = "image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String originFileName; // original 파일 이름

    private String storeFileName; // store 파일 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; // 게시글

    @Builder
    public Image(String originFileName, String storeFileName) {
        this.originFileName = originFileName;
        this.storeFileName = storeFileName;
    }

    // 연관관계 편의 메서드
    public void setBoard(Board board) {
        this.board = board;
        board.getImages().add(this);
    }
}
