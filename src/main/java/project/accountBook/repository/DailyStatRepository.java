package project.accountBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.accountBook.entity.DailyStat;

public interface DailyStatRepository extends JpaRepository<DailyStat, Long> {
}
