package project.accountBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.accountBook.entity.AccountBook;
import project.accountBook.entity.User;

import java.util.List;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    @Query("select a from AccountBook a where a.user = :user and a.years = :years and a.months = :months")
    AccountBook findByUserAndYearsAndMonths(User user, int years, int months);
}
