package project.accountBook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @OneToMany(mappedBy = "payment")
    private List<DailyStat> dailyStats = new ArrayList<>();


    public Payment(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
