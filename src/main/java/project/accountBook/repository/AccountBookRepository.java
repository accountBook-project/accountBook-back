package project.accountBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.accountBook.entity.AccountBook;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
}
