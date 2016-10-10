package ua.fantotsy.repository.customer;

import ua.fantotsy.domain.Customer;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final List<Customer> customers;

    public InMemoryCustomerRepository() {
        customers = new ArrayList<>();
    }

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

    private long getNextId() {
        return (customers.size() + 1);
    }
}