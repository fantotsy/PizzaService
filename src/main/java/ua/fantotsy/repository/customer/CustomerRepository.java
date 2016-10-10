package ua.fantotsy.repository.customer;

import ua.fantotsy.domain.Customer;

public interface CustomerRepository {

    Customer getCustomerById(long id);

    void addNewCustomer(Customer customer);
}