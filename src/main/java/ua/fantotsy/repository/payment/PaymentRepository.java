package ua.fantotsy.repository.payment;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Payment;

@Repository("paymentRepository")
public interface PaymentRepository {

    Payment findById(Long id);
}