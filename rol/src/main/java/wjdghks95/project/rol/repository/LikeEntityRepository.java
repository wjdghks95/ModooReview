package wjdghks95.project.rol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wjdghks95.project.rol.domain.entity.LikeEntity;
import wjdghks95.project.rol.domain.entity.Member;
import wjdghks95.project.rol.domain.entity.Review;

import java.util.Optional;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByMemberAndReview(Member member,Review review);
}
