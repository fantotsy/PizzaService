package ua.fantotsy.services.customer;

import ua.fantotsy.domain.Customer;

public interface CustomerService {

    Customer getCustomerById(Long id);

    Customer addNewCustomer(String name, String city, String street, Boolean hasAccumulativeCard);
}