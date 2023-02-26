package com.io.rol.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 태그 Entity
@Entity(name = "tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    @Column(name = "name")
    private String name; // 이름

    @OneToMany(mappedBy = "tag")
    private List<BoardTag> boardTagList = new ArrayList<>(); // boardTag 목록

    @Builder
    public Tag(String name) {
        this.name = name;
    }
}
