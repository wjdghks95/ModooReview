package com.io.rol.member.domain.entity;

import com.io.rol.common.auditing.BaseTimeEntity;
import com.io.rol.board.domain.entity.Board;
import com.io.rol.like.domain.entity.Like;
import com.io.rol.follow.domain.entity.Follow;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 회원 Entity
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // primary key

    @Column(unique = true, nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 이름

    @Column(unique = true, nullable = false)
    private String nickname; // 닉네임

    @Column(nullable = false)
    private String zipcode; // 우편번호

    @Column(nullable = false)
    private String address; // 주소

    private String detailAddress; // 상세주소

    @Column(nullable = false)
    private String phone; // 핸드폰 번호

    private String profileImage; // 프로필 사진

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // 역할

    // 회원 탈퇴시 회원이 작성한 게시글 모두 삭제
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>(); // 게시글 목록

    // 회원 탈퇴시 팔로우, 팔로잉 목록 모두 삭제
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followingList = new ArrayList<>(); // 팔로잉 목록

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followerList = new ArrayList<>(); // 팔로워 목록

    // 회원 탈퇴시 회원의 좋아요 모두 삭제
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>(); // 좋아요 목록

    @Builder
    public Member(String email, String password, String name, String nickname, String zipcode, String address, String detailAddress, String phone, String profileImage, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.phone = phone;
        this.profileImage = profileImage;
        this.role = role;
    }

    // 닉네임 변경
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // 프로필 사진 변경
    public void setProfileImg(String storeFileName) {
        this.profileImage = "/image/" + storeFileName;
    }

    // 비밀번호 변경
    public void setPassword(String tempPwd) {
        this.password = tempPwd;
    }
}
