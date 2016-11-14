package ua.fantotsy.repository.customer;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Customer;

import java.util.ArrayList;
import java.util.List;

@Repository()
public class InMemoryCustomerRepository implements CustomerRepository {
    /*Fields*/
    private final List<Customer> customers;

    /*Constructors*/
    public InMemoryCustomerRepository() {
        customers = new ArrayList<>();
    }

    /*Public Methods*/
    @Override
    public Customer findById(Long id) {
        if (customers.size() > 0) {
            for (Customer customer : customers) {
                if (customer.getCustomerId().equals(id)) {
                    return customer;
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public Customer findByName(String name) {
        if (customers.size() > 0) {
            for (Customer customer : customers) {
                if (customer.getName().equals(name)) {
                    return customer;
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public Customer save(Customer customer) {
        customer.setCustomerId(getNextId());
        customers.add(customer);
        return customer;
    }

    /*Private Methods*/
    private long getNextId() {
        return (customers.size() + 1);
    }
}