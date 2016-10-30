package ua.fantotsy.services.customer;

import ua.fantotsy.domain.Customer;

public interface CustomerService {

    Customer findById(Long id);

    Customer findByName(String name);

    Customer addNewCustomer(String name, String city, String street, Boolean hasAccumulativeCard);
}