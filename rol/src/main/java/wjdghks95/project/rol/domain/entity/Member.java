package wjdghks95.project.rol.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wjdghks95.project.rol.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String zipcode;

    private String address;

    private String detailAddress;

    private String phone;

    @Lob
    private String profileImage;

    private String role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followingList = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, String nickname, String zipcode, String address, String detailAddress, String phone, String profileImage, String role) {
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
