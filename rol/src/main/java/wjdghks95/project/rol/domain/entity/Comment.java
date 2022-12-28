package wjdghks95.project.rol.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wjdghks95.project.rol.domain.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Comment(String content) {
        this.content = content;
    }

    /** 연관관계 메서드 */
    public void setMember(Member member) {
        this.member = member;
    }

    public void setReview(Review review) {
        this.review = review;
        review.getCommentList().add(this);
    }
}
