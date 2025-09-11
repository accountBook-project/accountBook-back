package project.accountBook.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.DailyStatDto;
import project.accountBook.entity.*;
import project.accountBook.repository.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DailyStatServiceTest {

    @Autowired
    private EmotionRepository emotionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DailyStatEmotionRepository dailyStatEmotionRepository;
    @Autowired
    private DailyStatRepository dailyStatRepository;
    @Autowired
    private DailyStatService dailyStatService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void dailyStatTest() throws Exception {
        //given
        User user = new User("sangjae", "ssiides12#", "ROLE_USER");
        userRepository.save(user);

        Emotion emotion = new Emotion(EmotionType.happy);
        Emotion emotion2 = new Emotion(EmotionType.sad);
        emotionRepository.save(emotion);
        emotionRepository.save(emotion2);

        Category category = new Category(CategoryType.A);
        categoryRepository.save(category);

        DailyStat dailyStat = new DailyStat(LocalDate.of(2025, 9, 3), 1000L,
                "안녕하세요 오늘 날씨가 참 좋네요.", DailyStatType.INCOME, user);

        DailyStatEmotion dailyStatEmotion = new DailyStatEmotion();
        dailyStatEmotion.setDailyStat(dailyStat);
        dailyStatEmotion.setEmotion(emotion);
        dailyStatEmotionRepository.save(dailyStatEmotion);
        DailyStatEmotion dailyStatEmotion1 = new DailyStatEmotion();
        dailyStatEmotion1.setEmotion(emotion2);
        dailyStatEmotion1.setDailyStat(dailyStat);
        dailyStatEmotionRepository.save(dailyStatEmotion1);

        dailyStat.addCategory(category);
        dailyStat.addDailyStatEmotion(dailyStatEmotion);
        dailyStat.addDailyStatEmotion(dailyStatEmotion1);
        dailyStatRepository.save(dailyStat);
        //when

        DailyStatDto stat = dailyStatService.findDailyStat(user.getId(), LocalDate.of(2025, 9, 3));


        //then
        System.out.println(stat);
        assertThat(stat.getDate()).isEqualTo("2025-09-03");
    }

}