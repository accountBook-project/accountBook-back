package project.accountBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.accountBook.entity.Category;
import project.accountBook.entity.CategoryType;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryType(CategoryType categoryType);
}
