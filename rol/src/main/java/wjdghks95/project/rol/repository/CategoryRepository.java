package wjdghks95.project.rol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wjdghks95.project.rol.domain.entity.Category;
import wjdghks95.project.rol.domain.entity.CategoryName;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(CategoryName categoryName);
}
