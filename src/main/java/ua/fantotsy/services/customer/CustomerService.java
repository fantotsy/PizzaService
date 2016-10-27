package ua.fantotsy.services.customer;

import ua.fantotsy.domain.Customer;

public interface CustomerService {

    Customer findCustomerById(Long id);

    Customer findCustomerByName(String name);

    Customer addNewCustomer(String name, String city, String street, Boolean hasAccumulativeCard);
}