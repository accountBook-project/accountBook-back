package project.accountBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.accountBook.entity.DailyStat;
import project.accountBook.entity.DailyStatType;
import project.accountBook.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyStatRepository extends JpaRepository<DailyStat, Long> {

    List<DailyStat> findByUserAndDate(User user, LocalDate date);


    @Query("select d from DailyStat d where d.user = :user and d.dailyStatType = :dailyStatType" +
            " and d.date between :start and :end")
    List<DailyStat> findPercentageBy(User user, LocalDate start, LocalDate end, DailyStatType dailyStatType);

}
