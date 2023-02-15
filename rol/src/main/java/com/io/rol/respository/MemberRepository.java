package com.io.rol.respository;

import com.io.rol.domain.dto.FindIdDto;
import com.io.rol.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email); // 이메일 존재 유무
    boolean existsByNickname(String nickname); // 닉네임 존재 유무
    Optional<Member> findByEmail(String email);

    Member findByNameAndPhone(String name, String phone);
}
