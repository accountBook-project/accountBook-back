package project.accountBook.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    private String userKey; // provicder + id oauth2식별자
    private String username;
    private String email;
    private String role;
    private int total_point;


    @OneToMany(mappedBy = "user")
    private List<AccountBook> accountBooks = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<DailyStat> dailyStats = new ArrayList<>();
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserSetting userSetting;


    public User(String userKey, String username, String email, String role) {
        this.userKey = userKey;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public void check(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
