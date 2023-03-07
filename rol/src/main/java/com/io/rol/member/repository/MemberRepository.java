package com.io.rol.member.repository;

import com.io.rol.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email); // 이메일 존재 유무
    boolean existsByNickname(String nickname); // 닉네임 존재 유무
    Optional<Member> findByEmail(String email); // 이메일로 조회

    Member findByNameAndPhone(String name, String phone); // 이름과 핸드폰 번호로 조회

    Member findByEmailAndPhone(String email, String phone); // 이메일과 핸드폰 번호로 조회
}
