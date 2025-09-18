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
import java.util.List;

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
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void 가계부_조회_테스트() throws Exception {
        //given
        User user = new User("sangjae", "ssiides12#", "ROLE_USER");
        userRepository.save(user);

        Emotion emotion = new Emotion(EmotionType.기분전환);
        Emotion emotion2 = new Emotion(EmotionType.슬픔);
        emotionRepository.save(emotion);
        emotionRepository.save(emotion2);

        Category category = new Category(CategoryType.식비);
        categoryRepository.save(category);
        Payment payment = new Payment(PaymentType.현금);
        paymentRepository.save(payment);

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

        dailyStat.addPayment(payment);
        dailyStat.addCategory(category);
        dailyStat.addDailyStatEmotion(dailyStatEmotion);
        dailyStat.addDailyStatEmotion(dailyStatEmotion1);
        dailyStatRepository.save(dailyStat);
        //when

        List<DailyStatDto> stat = dailyStatService.findDailyStat(user.getId(), LocalDate.of(2025, 9, 3));


        //then
        System.out.println(stat);
        assertThat(stat.get(0).getDate()).isEqualTo("2025-09-03");
    }

    @Test
    public void 가계부_저장_테스트() throws Exception {
        //given
        User user = new User("sangjae", "abc", "ROLE_USER");
        userRepository.save(user);
        DailyStatDto dailyStatDto = new DailyStatDto(LocalDate.of(2025, 9, 1), 1000L, "안녕하세요",
                DailyStatType.INCOME, CategoryType.식비, PaymentType.현금, List.of(EmotionType.슬픔, EmotionType.기분전환));
        DailyStatDto dailyStatDto2 = new DailyStatDto(LocalDate.of(2025, 9, 1), 3000L, "안녕하세요",
                DailyStatType.EXPENSE, CategoryType.식비, PaymentType.기타, List.of(EmotionType.flex, EmotionType.기분전환));
        //when
        System.out.println(dailyStatDto.getPaymentType());
        dailyStatService.save(dailyStatDto, user.getId().toString());
        dailyStatService.save(dailyStatDto2, user.getId().toString());
        List<DailyStat> data = dailyStatRepository.findByUserAndDate(user, LocalDate.of(2025, 9, 1));
        //then
        assertThat(data.get(0).getMoney()).isEqualTo(1000);
        assertThat(data.get(1).getMoney()).isEqualTo(3000);

    }


}