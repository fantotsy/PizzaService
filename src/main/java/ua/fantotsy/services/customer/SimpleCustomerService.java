package ua.fantotsy.services.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.customer.CustomerRepository;
import ua.fantotsy.services.address.AddressService;

public class SimpleCustomerService implements CustomerService {
    /*Fields*/
    private CustomerRepository customerRepository;
    private AddressService addressService;

    /*Constructors*/
    @Autowired
    public SimpleCustomerService(CustomerRepository customerRepository, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
    }

    /*Public Methods*/
    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public void addNewCustomer(String name, String city, String street, boolean hasAccumulativeCard) {
        Customer newCustomer = createNewCustomer();
        newCustomer.setName(name);

        Address address = addressService.addAndReturnNewAddress(city, street);
        newCustomer.setAddress(address);

        if (hasAccumulativeCard) {
            AccumulativeCard accumulativeCard = createNewAccumulativeCard();
            newCustomer.setAccumulativeCard(accumulativeCard);
        }

        customerRepository.addNewCustomer(newCustomer);
    }

    /*Private & Protected Methods*/
    protected Customer createNewCustomer() {
        throw new IllegalStateException();
    }

    protected AccumulativeCard createNewAccumulativeCard() {
        throw new IllegalStateException();
    }
}