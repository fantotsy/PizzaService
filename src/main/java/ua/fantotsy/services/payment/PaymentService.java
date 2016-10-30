package ua.fantotsy.services.payment;

import ua.fantotsy.domain.Payment;

public interface PaymentService {

    Payment findById(Long id);
}