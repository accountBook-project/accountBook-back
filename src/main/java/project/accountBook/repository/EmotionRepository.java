package project.accountBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.accountBook.entity.Emotion;
import project.accountBook.entity.EmotionType;

import java.util.List;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Emotion findByEmotionType(EmotionType emotionType);
}
