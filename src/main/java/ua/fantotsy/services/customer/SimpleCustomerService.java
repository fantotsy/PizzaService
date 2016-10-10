package ua.fantotsy.services.customer;

import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.customer.CustomerRepository;

public class SimpleCustomerService implements CustomerService {
    /*Fields*/
    private CustomerRepository customerRepository;

    /*Constructors*/
    public SimpleCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*Methods*/
    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public void addNewCustomer(Customer customer) {
        customerRepository.addNewCustomer(customer);
    }
}