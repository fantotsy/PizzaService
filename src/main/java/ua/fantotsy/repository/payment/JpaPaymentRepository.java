package ua.fantotsy.repository.payment;

import ua.fantotsy.domain.Payment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaPaymentRepository implements PaymentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Payment findById(Long id) {
        return entityManager.find(Payment.class, id);
    }
}