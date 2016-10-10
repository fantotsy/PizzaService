package ua.fantotsy.services.customer;

import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.customer.CustomerRepository;

public class SimpleCustomerService implements CustomerService {
    private CustomerRepository customerRepository;

    public SimpleCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public void addNewCustomer(Customer customer) {
        customerRepository.addNewCustomer(customer);
    }
}