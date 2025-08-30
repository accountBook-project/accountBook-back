package project.accountBook.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.DailyStatDto;
import project.accountBook.entity.*;
import project.accountBook.repository.DailyStatEmotionRepository;
import project.accountBook.repository.DailyStatRepository;
import project.accountBook.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyStatService {

    private final DailyStatRepository dailyStatRepository;
    private final UserRepository userRepository;


    public DailyStatDto findDailyStat(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(null);
        DailyStat userDailyStat = dailyStatRepository.findByUserAndDate(user, date).orElseThrow(
                () -> new RuntimeException("해당 날짜에 데이터가 없습니다."));

        List<DailyStatEmotion> dailyStatEmotions = userDailyStat.getDailyStatEmotions();
        List<EmotionType> emotionList = dailyStatEmotions.stream()
                .map(dailyStatEmotion -> dailyStatEmotion.getEmotion().getEmotionType())
                .toList();

        DailyStatDto dailyStatDto = new DailyStatDto(userDailyStat.getDate(), userDailyStat.getMoney(), userDailyStat.getDescriptions(),
                userDailyStat.getDailyStatType(), userDailyStat.getCategory().getCategoryType(), emotionList);

        return dailyStatDto;
    }


}
