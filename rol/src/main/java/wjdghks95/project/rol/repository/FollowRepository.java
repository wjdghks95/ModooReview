package wjdghks95.project.rol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wjdghks95.project.rol.domain.entity.Follow;
import wjdghks95.project.rol.domain.entity.Member;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query("select f from Follow f where f.following = :following and f.follower = :follower")
    Optional<Follow> findFollow(@Param("following")Member following, @Param("follower") Member follower);
}
