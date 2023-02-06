package com.io.rol.respository;

import com.io.rol.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String tagName);
    boolean existsByName(String tagName);
}
