package project.accountBook.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "account_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountBook extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int years;
    private int months;
    @Setter
    private long totalIncome;
    @Setter
    private long totalExpense;

    public AccountBook(User user, int years, int months, long totalIncome, long totalExpense) {
        this.user = user;
        this.years = years;
        this.months = months;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }
}
