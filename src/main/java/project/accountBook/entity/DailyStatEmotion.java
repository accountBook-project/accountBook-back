package project.accountBook.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DailyStatEmotion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dailystat_emotion_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_stat_id")
    private DailyStat dailyStat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id")
    private Emotion emotion;

}
