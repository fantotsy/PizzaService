package ua.fantotsy.repository.customer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.RepositoryTestConfig;

import static org.junit.Assert.assertNotNull;

public class JpaCustomerRepositoryIT extends RepositoryTestConfig {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFind() {

    }

    @Test
    public void testSaveCustomer() {
        Address address = new Address("City", "Street");
        AccumulativeCard accumulativeCard = new AccumulativeCard("1234567887654321");
        Customer customer = new Customer("Name", address, accumulativeCard);
        customer = customerRepository.save(customer);
        assertNotNull(customer.getId());
    }
}