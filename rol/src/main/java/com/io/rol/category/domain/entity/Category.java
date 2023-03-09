package com.io.rol.category.domain.entity;

import com.io.rol.board.domain.entity.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 카테고리 Entity
@Entity(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id; // primary key

    private String name; // 이름

    @OneToMany(mappedBy = "category")
    private List<Board> boardList = new ArrayList<>(); // 게시글 목록

    @Builder
    public Category(String name) {
        this.name = name;
    }
}
