package project.accountBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.accountBook.entity.Payment;
import project.accountBook.entity.PaymentType;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByPaymentType(PaymentType paymentType);
}
