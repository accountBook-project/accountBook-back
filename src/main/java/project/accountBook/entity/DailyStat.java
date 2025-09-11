package project.accountBook.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyStat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;
    private String descriptions;
    private Long money;

    @Enumerated(EnumType.STRING)
    private DailyStatType dailyStatType;

    @OneToMany(mappedBy = "dailyStat")
    private List<DailyStatEmotion> dailyStatEmotions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public DailyStat(LocalDate date, Long money, String descriptions, DailyStatType type, User user) {
        this.date = date;
        this.money = money;
        this.descriptions = descriptions;
        this.dailyStatType = type;
        this.user = user;
    }

    public void addCategory(Category category) {
        this.category = category;
        category.getDailyStats().add(this);
    }
    public void addDailyStatEmotion(DailyStatEmotion dailyStatEmotion) {
        dailyStatEmotions.add(dailyStatEmotion);
        dailyStatEmotion.setDailyStat(this);
    }
}
