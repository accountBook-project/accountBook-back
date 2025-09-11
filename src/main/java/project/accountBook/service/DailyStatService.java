package project.accountBook.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.DailyStatDto;
import project.accountBook.entity.*;
import project.accountBook.repository.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyStatService {

    private final DailyStatRepository dailyStatRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EmotionRepository emotionRepository;
    private final DailyStatEmotionRepository dailyStatEmotionRepository;


    public DailyStatDto findDailyStat(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        DailyStat userDailyStat = dailyStatRepository.findByUserAndDate(user, date).orElseThrow(
                () -> new RuntimeException("해당 날짜에 데이터가 없습니다."));

        List<DailyStatEmotion> dailyStatEmotions = userDailyStat.getDailyStatEmotions();
        List<EmotionType> emotionList = dailyStatEmotions.stream()
                .map(dailyStatEmotion -> dailyStatEmotion.getEmotion().getEmotionType())
                .toList();

        return new DailyStatDto(userDailyStat.getDate(), userDailyStat.getMoney(), userDailyStat.getDescriptions(),
                userDailyStat.getDailyStatType(), userDailyStat.getCategory().getCategoryType(), emotionList);
    }


    public void save(DailyStatDto dailyStatDto, String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        DailyStat dailyStat = new DailyStat(dailyStatDto.getDate(), dailyStatDto.getMoney(),
                dailyStatDto.getDescriptions(), dailyStatDto.getType(), user);

        Category category = categoryRepository.findByCategoryType(dailyStatDto.getCategoryType());
        dailyStat.addCategory(category);
        dailyStatDto.getEmotionList().stream()
                .forEach(emotionType -> {
                    Emotion emotion = emotionRepository.findByEmotionType(emotionType);
                    DailyStatEmotion dailyStatEmotion = new DailyStatEmotion();
                    dailyStatEmotion.setDailyStat(dailyStat);
                    dailyStatEmotion.setEmotion(emotion);
                    dailyStatEmotionRepository.save(dailyStatEmotion);
                    dailyStat.addDailyStatEmotion(dailyStatEmotion);
                });
        dailyStatRepository.save(dailyStat);
        int year = dailyStatDto.getDate().getYear();
        int month = dailyStatDto.getDate().getMonth().getValue();



    }
}
