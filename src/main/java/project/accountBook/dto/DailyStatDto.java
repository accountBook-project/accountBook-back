package project.accountBook.dto;


import lombok.Data;
import project.accountBook.entity.CategoryType;
import project.accountBook.entity.DailyStatType;
import project.accountBook.entity.Emotion;
import project.accountBook.entity.EmotionType;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyStatDto {

    private LocalDate date;
    private Long money;
    private String descriptions;
    private DailyStatType type;
    private CategoryType categoryType;
    private List<EmotionType> emotionList;

    public DailyStatDto(LocalDate date, Long money, String descriptions, DailyStatType type, CategoryType categoryType, List<EmotionType> emotionList) {
        this.date = date;
        this.money = money;
        this.descriptions = descriptions;
        this.type = type;
        this.categoryType = categoryType;
        this.emotionList = emotionList;
    }
}
