package project.accountBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.accountBook.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
