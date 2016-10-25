package ua.fantotsy.repository.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.*;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.repository.RepositoryTestConfig;
import ua.fantotsy.repository.accumulativeCard.AccumulativeCardRepository;
import ua.fantotsy.repository.address.AddressRepository;
import ua.fantotsy.repository.customer.CustomerRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public class JpaOrderRepositoryIT extends RepositoryTestConfig {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccumulativeCardRepository accumulativeCardRepository;

    @Test
    public void testFind() {

    }

    @Test
    public void testSavePizza() {
        Address address = addressRepository.save(new Address("City", "Street"));
        AccumulativeCard accumulativeCard = accumulativeCardRepository.save(new AccumulativeCard("1234567887654321"));
        Customer customer = customerRepository.save(new Customer("Name", address, accumulativeCard));
        Order order = new Order(new HashMap<Pizza, Integer>(), customer, new HashSet<Discount>());
        order = orderRepository.save(order);
        assertNotNull(order.getId());
    }
}