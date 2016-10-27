package ua.fantotsy.services.payment;

import ua.fantotsy.domain.Payment;

public interface PaymentService {

    Payment findPaymentById(Long id);
}