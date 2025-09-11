package project.accountBook.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "emotion")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;


    public Emotion(EmotionType emotionType) {
        this.emotionType = emotionType;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "emotion")
    private List<DailyStatEmotion> dailyStatEmotions = new ArrayList<>();
}
