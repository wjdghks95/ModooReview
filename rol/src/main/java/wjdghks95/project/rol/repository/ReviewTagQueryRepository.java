package wjdghks95.project.rol.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wjdghks95.project.rol.domain.entity.ReviewTag;
import wjdghks95.project.rol.domain.entity.Tag;

public interface ReviewTagQueryRepository {

    Page<ReviewTag> findReviewTagList(Pageable pageable, Tag tag);
}
