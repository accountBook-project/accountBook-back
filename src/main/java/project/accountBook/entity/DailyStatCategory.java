package project.accountBook.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DailyStatCategory {

    @EmbeddedId
    private DailyStatCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dailyStatId")
    @JoinColumn(name = "daily_stat_id")
    private DailyStat dailyStat;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    @JoinColumn(name = "emotion_id")
    private Category category;
}
