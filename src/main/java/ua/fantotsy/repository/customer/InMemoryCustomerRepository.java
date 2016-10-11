package ua.fantotsy.repository.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Customer;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {
    /*Fields*/
    private final List<Customer> customers;

    /*Constructors*/
    public InMemoryCustomerRepository() {
        customers = new ArrayList<>();
    }

    /*Public Methods*/
    @Override
    public Customer getCustomerById(long id) {
        if (customers.size() > 0) {
            for (Customer customer : customers) {
                if (customer.getId() == id) {
                    return customer;
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public void addNewCustomer(Customer customer) {
        customer.setId(getNextId());
        customers.add(customer);
    }

    /*Private Methods*/
    private long getNextId() {
        return (customers.size() + 1);
    }
}