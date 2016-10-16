package ua.fantotsy.services.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.customer.CustomerRepository;
import ua.fantotsy.services.accumulativeCard.AccumulativeCardService;
import ua.fantotsy.services.address.AddressService;

public class SimpleCustomerService implements CustomerService {
    /*Fields*/
    private CustomerRepository customerRepository;
    private AddressService addressService;
    private AccumulativeCardService accumulativeCardService;

    /*Constructors*/
    @Autowired
    public SimpleCustomerService(CustomerRepository customerRepository, AddressService addressService,
                                 AccumulativeCardService accumulativeCardService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.accumulativeCardService = accumulativeCardService;
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
            AccumulativeCard accumulativeCard = accumulativeCardService.addAndReturnNewAccumulativeCard();
            newCustomer.setAccumulativeCard(accumulativeCard);
        }
        customerRepository.addNewCustomer(newCustomer);
    }

    /*Private & Protected Methods*/
    protected Customer createNewCustomer() {
        throw new IllegalStateException();
    }
}