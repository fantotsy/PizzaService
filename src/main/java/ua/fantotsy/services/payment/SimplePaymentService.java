package ua.fantotsy.services.payment;

import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.Payment;
import ua.fantotsy.repository.payment.PaymentRepository;

public class SimplePaymentService implements PaymentService {
    /*Fields*/
    private PaymentRepository paymentRepository;

    /*Constructors*/
    @Autowired
    public SimplePaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /*Public Methods*/
    @Override
    public Payment findPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
}