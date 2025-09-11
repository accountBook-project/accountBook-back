package project.accountBook.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.accountBook.entity.DailyStat;
import project.accountBook.entity.DailyStatEmotion;

import java.util.List;

public interface DailyStatEmotionRepository extends JpaRepository<DailyStatEmotion, Long> {
}
