package ua.fantotsy.repository.customer;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.fantotsy.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("customerRepository")
public class JpaCustomerRepository implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public Customer findByName(String name) {
        return entityManager.createNamedQuery("Customer.findByName", Customer.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public Customer save(Customer customer) {
        return entityManager.merge(customer);
    }
}