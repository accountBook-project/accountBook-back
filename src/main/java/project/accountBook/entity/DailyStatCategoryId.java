package project.accountBook.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Embeddable
@EqualsAndHashCode
public class DailyStatCategoryId implements Serializable {

    private Long categoryId;
    private Long dailyStatId;
}
