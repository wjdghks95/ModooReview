package wjdghks95.project.rol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wjdghks95.project.rol.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
