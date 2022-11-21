package wjdghks95.project.rol.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private Member following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private Member follower;

    @Builder
    public Follow(Member following, Member follower) {
        this.following = following;
        this.follower = follower;
    }

    /** 연관관계 메서드 */
    public void setFollowing(Member following) {
        this.following = following;
        following.getFollowingList().add(this);
    }

    public void setFollower(Member follower) {
        this.follower = follower;
        follower.getFollowerList().add(this);
    }
}
