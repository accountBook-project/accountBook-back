package project.accountBook.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.DailyStatDto;
import project.accountBook.entity.*;
import project.accountBook.repository.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
    private final AccountBookRepository accountBookRepository;
    private final PaymentRepository paymentRepository;


    public List<DailyStatDto> findDailyStat(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        List<DailyStat> userDailyStat = dailyStatRepository.findByUserAndDate(user, date);
        List<DailyStatDto> dailyStatDto = new ArrayList<>();
        userDailyStat.stream()
                .forEach(dailyStat -> {
                    List<DailyStatEmotion> dailyStatEmotions = dailyStat.getDailyStatEmotions();
                    List<EmotionType> emotionList = dailyStatEmotions.stream()
                            .map(dailyStatEmotion -> dailyStatEmotion.getEmotion().getEmotionType())
                            .toList();

                    dailyStatDto.add(new DailyStatDto(dailyStat.getDate(), dailyStat.getMoney(), dailyStat.getDescriptions(),
                            dailyStat.getDailyStatType(), dailyStat.getCategory().getCategoryType(), dailyStat.getPayment().getPaymentType(), emotionList));
                });
        return dailyStatDto;
    }

    @Transactional
    public void save(DailyStatDto dailyStatDto, String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        DailyStat dailyStat = new DailyStat(dailyStatDto.getDate(), dailyStatDto.getMoney(),
                dailyStatDto.getDescriptions(), dailyStatDto.getType(), user);

        Category category = categoryRepository.findByCategoryType(dailyStatDto.getCategoryType());
        Payment payment = paymentRepository.findByPaymentType(dailyStatDto.getPaymentType());
        dailyStat.addCategory(category);
        dailyStat.addPayment(payment);
        dailyStatRepository.save(dailyStat);
        dailyStatDto.getEmotionList().stream()
                .forEach(emotionType -> {
                    Emotion emotion = emotionRepository.findByEmotionType(emotionType);
                    DailyStatEmotion dailyStatEmotion = new DailyStatEmotion();
                    dailyStatEmotion.setDailyStat(dailyStat);
                    dailyStatEmotion.setEmotion(emotion);
                    dailyStatEmotionRepository.save(dailyStatEmotion);
                    dailyStat.addDailyStatEmotion(dailyStatEmotion);
                });
        int year = dailyStatDto.getDate().getYear();
        int month = dailyStatDto.getDate().getMonth().getValue();
        AccountBook accountBook = accountBookRepository.findByUserDate(user, year, month).orElse(null);
        if(accountBook == null) {
            accountBookRepository.save(new AccountBook(user, year, month,
                    dailyStatDto.getType() == DailyStatType.INCOME ? dailyStatDto.getMoney() : 0,
                    dailyStatDto.getType() == DailyStatType.EXPENSE ? dailyStatDto.getMoney() : 0));
        } else if(dailyStatDto.getType() == DailyStatType.EXPENSE) {
            accountBook.setTotalExpense(accountBook.getTotalExpense()+dailyStatDto.getMoney());
        } else {
            accountBook.setTotalIncome(accountBook.getTotalIncome()+dailyStatDto.getMoney());
        }


    }
}
