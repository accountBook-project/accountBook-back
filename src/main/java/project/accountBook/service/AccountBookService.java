package project.accountBook.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.AccountBookDto;
import project.accountBook.entity.*;
import project.accountBook.repository.AccountBookRepository;
import project.accountBook.repository.DailyStatRepository;
import project.accountBook.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final DailyStatRepository dailyStatRepository;
    private final UserRepository userRepository;


    public AccountBookDto findMonthData(Long userId, int year, int month, DailyStatType dailyStatType) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new AccountBookDto(year, month, 0, 0, null);
        }
        AccountBook userAccountBook = accountBookRepository.findByUserAndYearsAndMonths(user, year, month);
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        List<DailyStat> percent = dailyStatRepository.findPercentageBy(user, start, end, dailyStatType);

        Map<CategoryType, Long> map = percent.stream()
                .collect(Collectors.groupingBy(dailyStat -> dailyStat.getCategory().getCategoryType(),
                        Collectors.summingLong(sum -> sum.getMoney() * 100 /
                                ((dailyStatType == DailyStatType.INCOME) ? userAccountBook.getTotalIncome()
                                        : userAccountBook.getTotalExpense()))));


        return new AccountBookDto(userAccountBook.getYears(), userAccountBook.getMonths(),
                userAccountBook.getTotalIncome(), userAccountBook.getTotalExpense(), map);
    }



}
