package ua.fantotsy.repository.customer;

import ua.fantotsy.domain.Customer;

public interface CustomerRepository {

    Customer findById(Long id);

    Customer findCustomerByName(String name);

    Customer save(Customer customer);
}