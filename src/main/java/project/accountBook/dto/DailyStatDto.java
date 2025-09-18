package project.accountBook.dto;


import lombok.Data;
import project.accountBook.entity.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyStatDto {

    private LocalDate date;
    private Long money;
    private String descriptions;
    private DailyStatType type;
    private CategoryType categoryType;
    private PaymentType paymentType;
    private List<EmotionType> emotionList;

    public DailyStatDto(LocalDate date, Long money, String descriptions, DailyStatType type, CategoryType categoryType, PaymentType paymentType, List<EmotionType> emotionList) {
        this.date = date;
        this.money = money;
        this.descriptions = descriptions;
        this.type = type;
        this.categoryType = categoryType;
        this.emotionList = emotionList;
        this.paymentType = paymentType;
    }
}
