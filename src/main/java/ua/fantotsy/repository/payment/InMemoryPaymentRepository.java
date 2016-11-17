package ua.fantotsy.repository.payment;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Payment;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryPaymentRepository implements PaymentRepository {
    /*Fields*/
    private final List<Payment> payments;

    /*Constructors*/
    public InMemoryPaymentRepository() {
        payments = new ArrayList<>();
    }

    /*Public Methods*/
    @Override
    public Payment findById(Long id) {
        if (payments.size() > 0) {
            for (Payment payment : payments) {
                if (payment.getPaymentId().equals(id)) {
                    return payment;
                }
            }
        }
        throw new RuntimeException();
    }
}