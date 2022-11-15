package wjdghks95.project.rol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wjdghks95.project.rol.domain.entity.ReviewTag;
import wjdghks95.project.rol.domain.entity.Tag;

import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    @Query("select r from ReviewTag r where r.tag = :tag")
    List<ReviewTag> findAllReviewTag(Tag tag);
}
