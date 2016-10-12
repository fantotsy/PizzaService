package ua.fantotsy.services.customer;

import ua.fantotsy.domain.Customer;

public interface CustomerService {

    Customer getCustomerById(long id);

    void addNewCustomer(String name, String city, String street, boolean hasAccumulativeCard);
}