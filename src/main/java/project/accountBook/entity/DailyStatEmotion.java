package project.accountBook.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DailyStatEmotion {

    @EmbeddedId
    private DailyStatEmotionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dailyStatId")
    @JoinColumn(name = "daily_stat_id")
    private DailyStat dailyStat;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("emotionId")
    @JoinColumn(name = "emotion_id")
    private Emotion emotion;
}
