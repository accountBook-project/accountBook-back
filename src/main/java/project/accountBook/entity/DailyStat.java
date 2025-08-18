package project.accountBook.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private DailyStatType type;

    @OneToMany(mappedBy = "dailyStat")
    private List<DailyStatEmotion> dailyStatEmotions = new ArrayList<>();

    @OneToMany(mappedBy = "dailyStat")
    private List<DailyStatCategory> dailyStatCategories = new ArrayList<>();


}
