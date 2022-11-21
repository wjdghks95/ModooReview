package wjdghks95.project.rol.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public LikeEntity(Member member, Review review) {
        this.member = member;
        this.review = review;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getLikeList().add(this);
    }

    public void setReview(Review review) {
        this.review = review;
        review.getLikeList().add(this);
    }
}
