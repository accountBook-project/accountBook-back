package project.accountBook.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserSetting {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

}
