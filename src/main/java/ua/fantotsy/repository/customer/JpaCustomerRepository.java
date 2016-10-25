package ua.fantotsy.repository.customer;

import ua.fantotsy.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaCustomerRepository implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public Customer save(Customer customer) {
        return entityManager.merge(customer);
    }
}