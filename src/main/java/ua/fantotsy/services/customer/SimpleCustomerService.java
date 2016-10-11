package ua.fantotsy.services.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.customer.CustomerRepository;

@Service
public class SimpleCustomerService implements CustomerService {
    /*Fields*/
    private CustomerRepository customerRepository;

    /*Constructors*/
    @Autowired
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