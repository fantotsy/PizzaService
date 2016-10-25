package ua.fantotsy.repository.customer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.RepositoryTestConfig;
import ua.fantotsy.repository.accumulativeCard.AccumulativeCardRepository;
import ua.fantotsy.repository.address.AddressRepository;

import static org.junit.Assert.assertNotNull;

public class JpaCustomerRepositoryIT extends RepositoryTestConfig {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccumulativeCardRepository accumulativeCardRepository;

    @Test
    public void testFindCustomerById() {

    }

    @Test
    public void testSaveCustomer() {
        Address address = addressRepository.save(new Address("City", "Street"));
        AccumulativeCard accumulativeCard = accumulativeCardRepository.save(new AccumulativeCard("1234567887654321"));
        Customer customer = new Customer("Name", address, accumulativeCard);
        customer = customerRepository.save(customer);
        assertNotNull(customer.getId());
    }
}