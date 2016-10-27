package ua.fantotsy.repository.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.*;
import ua.fantotsy.repository.RepositoryTestConfig;
import ua.fantotsy.repository.accumulativeCard.AccumulativeCardRepository;
import ua.fantotsy.repository.address.AddressRepository;
import ua.fantotsy.repository.customer.CustomerRepository;

import java.util.HashMap;
import java.util.HashSet;

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
    public void testFindOrderById() {

    }

    @Test
    public void testSaveOrder() {
        Address address = addressRepository.save(new Address("City", "Street"));
        AccumulativeCard accumulativeCard = accumulativeCardRepository.save(new AccumulativeCard(12345L));
        Customer customer = customerRepository.save(new Customer("Name", address, accumulativeCard));
        Order order = new Order(new HashMap<>(), customer);
        order = orderRepository.save(order);
        assertNotNull(order.getId());
    }

    @Test
    public void testConfirmOrderById() {

    }

    @Test
    public void testPayOrderById() {

    }

    @Test
    public void testCancelOrderById() {

    }

    @Test
    public void testAddPizzaByOrderId() {

    }

    @Test
    public void testRemovePizzaByOrderId() {

    }

    @Test
    public void testGetNumberOfOrders() {

    }
}