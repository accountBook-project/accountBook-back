package project.accountBook.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.accountBook.entity.DailyStat;
import project.accountBook.entity.DailyStatEmotion;
import project.accountBook.entity.DailyStatEmotionId;

import java.util.List;

public interface DailyStatEmotionRepository extends JpaRepository<DailyStatEmotion, DailyStatEmotionId> {

    List<DailyStatEmotion> findByDailyStat(DailyStat dailyStat);
}
