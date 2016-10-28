package ua.fantotsy.repository.customer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.RepositoryTestConfig;
import ua.fantotsy.repository.accumulativeCard.AccumulativeCardRepository;
import ua.fantotsy.repository.address.AddressRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JpaCustomerRepositoryIT extends RepositoryTestConfig {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccumulativeCardRepository accumulativeCardRepository;

    @Before
    public void setUp() {
        Address address = new Address("City1", "Street1");
        jdbcTemplate.update("INSERT INTO addresses (id, city, street) VALUES (1, ?, ?)",
                address.getCity(), address.getStreet());

        AccumulativeCard accumulativeCard1 = new AccumulativeCard(12345L);
        jdbcTemplate.update("INSERT INTO accumulative_cards (id, balance, number) VALUES (1, ?, ?)",
                accumulativeCard1.getBalance(), accumulativeCard1.getNumber());

        jdbcTemplate.update("INSERT INTO customers (id, name, address_id, accumulative_card_id) VALUES (1, 'Name1', 1, 1)");
    }

    @After
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM customers");
        jdbcTemplate.update("DELETE FROM accumulative_cards");
        jdbcTemplate.update("DELETE FROM addresses");
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = customerRepository.findById(1L);
        long id = customer.getId();
        assertEquals(1L, id);
    }

    @Test
    public void testSaveCustomer() {
        AccumulativeCard accumulativeCard2 = new AccumulativeCard(12346L);
        jdbcTemplate.update("INSERT INTO accumulative_cards (id, balance, number) VALUES (2, ?, ?)",
                accumulativeCard2.getBalance(), accumulativeCard2.getNumber());

        Customer customer = new Customer("Name2", addressRepository.findById(1L), accumulativeCardRepository.findById(2L));
        customer = customerRepository.save(customer);
        assertNotNull(customer.getId());

        String savedCustomer = jdbcTemplate.queryForObject("SELECT name FROM customers WHERE name = ?",
                    new Object[]{customer.getName()}, String.class);
        System.out.println(savedCustomer);
        assertEquals(customer.getName(), savedCustomer);
    }
}