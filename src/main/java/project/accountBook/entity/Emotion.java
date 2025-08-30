package project.accountBook.entity;


import jakarta.persistence.*;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "emotion")
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;



    @JsonIgnore
    @OneToMany(mappedBy = "emotion")
    private List<DailyStatEmotion> dailyStatEmotions = new ArrayList<>();
}
