package com.io.rol.respository;

import com.io.rol.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findFollowByFollowerIdAndFollowingId(Long followerId, Long followingId); // 팔로워 아이디와 팔로잉 아이디로 조회
}
