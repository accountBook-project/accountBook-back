package project.accountBook.entity;


import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class DailyStatEmotionId implements Serializable {
    private Long dailyStatId;
    private Long emotionId;
}
