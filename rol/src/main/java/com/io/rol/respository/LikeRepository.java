package com.io.rol.respository;

import com.io.rol.domain.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberIdAndBoardId(Long memberId, Long boardId);
}
