package wjdghks95.project.rol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wjdghks95.project.rol.domain.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.review r where r.id = :id")
    List<Comment> findComments(@Param("id") Long id);
}
