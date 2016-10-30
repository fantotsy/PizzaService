package ua.fantotsy.repository.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.LinkedCaseInsensitiveMap;
import ua.fantotsy.domain.*;
import ua.fantotsy.domain.discounts.AccumulativeCardDiscount;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.domain.discounts.DiscountManager;
import ua.fantotsy.domain.discounts.TheMostExpensivePizzaDiscount;
import ua.fantotsy.repository.RepositoryTestConfig;
import ua.fantotsy.repository.address.AddressRepository;
import ua.fantotsy.repository.customer.CustomerRepository;
import ua.fantotsy.repository.pizza.PizzaRepository;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JpaOrderRepositoryIT extends RepositoryTestConfig {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    @Before
    public void setUp() {
        jdbcTemplate.update("INSERT INTO pizzas (id, name, price, status, type) VALUES (1, 'Name1', 100.0, 'AVAILABLE', 'SEA')");
        jdbcTemplate.update("INSERT INTO addresses (id, city, street) VALUES (1, 'City1', 'Street1')");
        jdbcTemplate.update("INSERT INTO accumulative_cards (id, balance, number) VALUES (1, 0.0, 12345)");
        jdbcTemplate.update("INSERT INTO customers (id, name, address_id, accumulative_card_id) VALUES (1, 'Name1', 1, 1)");

        if (DiscountManager.getActiveDiscounts() == null) {
            Set<Discount> discounts = new HashSet<Discount>() {{
                add(new AccumulativeCardDiscount());
                add(new TheMostExpensivePizzaDiscount());
            }};
            DiscountManager.setActiveDiscounts(discounts);
        }
    }

    @After
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM pizzas_quantities");
        jdbcTemplate.update("DELETE FROM orders");
        jdbcTemplate.update("DELETE FROM customers");
        jdbcTemplate.update("DELETE FROM accumulative_cards");
        jdbcTemplate.update("DELETE FROM addresses");
        jdbcTemplate.update("DELETE FROM pizzas");
    }

    @Test
    public void testFindPizzaById() {
        jdbcTemplate.update("INSERT INTO orders (id, status, address_id, customer_id) VALUES (1, 'NEW', 1, 1)");
        jdbcTemplate.update("INSERT INTO pizzas_quantities VALUES (1, 1, 1)");
        Order order = orderRepository.findById(1L);
        long id = order.getId();
        assertEquals(1L, id);
    }

    @Test
    public void testSetIdAfterSave() {
        Pizza pizza = pizzaRepository.findById(1L);
        Customer customer = customerRepository.findById(1L);
        Address address = addressRepository.findById(1L);
        Order order = new Order(new HashMap<Pizza, Integer>() {{
            put(pizza, 1);
        }}, customer, address);
        order = orderRepository.save(order);
        assertNotNull(order.getId());
    }

    @Test
    public void testGetNumberOfOrders() {
        jdbcTemplate.update("INSERT INTO orders (id, status, address_id, customer_id) VALUES (1, 'NEW', 1, 1)");
        jdbcTemplate.update("INSERT INTO orders (id, status, address_id, customer_id) VALUES (2, 'NEW', 1, 1)");
        long numberOfOrders = orderRepository.getNumberOfOrders();
        assertEquals(2, numberOfOrders);
    }

    private Order getOrderObjectFromDatabaseById(long id) {
        Map<Pizza, Integer> pizzas = new HashMap<>();
        List<Map<String, Object>> selectedPizzas = jdbcTemplate.queryForList("SELECT pizza_id, quantity FROM pizzas_quantities WHERE order_id = ?", id);
        for (Map<String, Object> map : selectedPizzas) {
            Pizza pizza = (Pizza) jdbcTemplate.queryForObject("SELECT * FROM pizzas WHERE id = ?", new Object[]{map.get("PIZZA_ID")},
                    new BeanPropertyRowMapper(Pizza.class));
            pizzas.put(pizza, (Integer) map.get("QUANTITY"));
        }
        int customer_id = 0;
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT customer_id FROM orders WHERE id = ?", new Object[]{id});
        while (rowSet.next()) {
            customer_id = rowSet.getInt("customer_id");
        }
        Customer customer = (Customer) jdbcTemplate.queryForObject("SELECT * FROM customers WHERE id = ?", new Object[]{customer_id}, new BeanPropertyRowMapper(Customer.class));

        int address_id = 0;
        rowSet = jdbcTemplate.queryForRowSet("SELECT address_id FROM orders WHERE id = ?", new Object[]{id});
        while (rowSet.next()) {
            address_id = rowSet.getInt("address_id");
        }
        Address address = (Address) jdbcTemplate.queryForObject("SELECT * FROM addresses WHERE id = ?", new Object[]{address_id}, new BeanPropertyRowMapper(Address.class));

        Integer payment_id = null;
        rowSet = jdbcTemplate.queryForRowSet("SELECT payment_id FROM orders WHERE id = ?", new Object[]{id});
        while (rowSet.next()) {
            payment_id = (Integer) rowSet.getInt("payment_id");
        }
        Payment payment = null;
        if (payment_id != 0) {
            payment = (Payment) jdbcTemplate.queryForObject("SELECT * FROM payments WHERE id = ?", new Object[]{payment_id}, new BeanPropertyRowMapper(Payment.class));
        }

        String status = null;
        rowSet = jdbcTemplate.queryForRowSet("SELECT status FROM orders WHERE id = ?", new Object[]{id});
        while (rowSet.next()) {
            status = rowSet.getString("status");
        }

        Order order = new Order(pizzas, customer, address);
        order.setPayment(payment);
        order.setStatus(Order.OrderStatus.valueOf(status));
        return new Order(pizzas, customer, address);
    }
}