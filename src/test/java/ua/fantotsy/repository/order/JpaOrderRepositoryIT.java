package ua.fantotsy.repository.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;
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
    @Ignore
    public void test(){
        Pizza pizza = pizzaRepository.findById(1L);
        Customer customer = customerRepository.findById(1L);
        Address address = addressRepository.findById(1L);
        Order order = new Order(new HashMap<Pizza, Integer>() {{
            put(pizza, 1);
        }}, customer, address);
        order = testt(order);
        long id = orderRepository.findOrdersByCustomerName("Name1").get(0).getId();
        order = testt2(id);
        //id = orderRepository.findOrdersByCustomerName("Name1").get(0).getId();
        Order newOrder = getOrderObjectFromDatabaseById(id);
        order = testt3(id);
        Order newOrder1 = getOrderObjectFromDatabaseById(id);
        System.out.println(newOrder);
    }

    @Transactional
    private Order testt(Order order){
        return orderRepository.save(order);
    }
    @Transactional
    private Order testt2(long id){
        return orderRepository.confirmById(id);
    }
    @Transactional
    private Order testt3(long id){
        return orderRepository.payById(id);
    }

    @Test
    public void testGetNumberOfOrders() {
        jdbcTemplate.update("INSERT INTO orders (id, status, address_id, customer_id) VALUES (1, 'NEW', 1, 1)");
        jdbcTemplate.update("INSERT INTO orders (id, status, address_id, customer_id) VALUES (2, 'NEW', 1, 1)");
        long numberOfOrders = orderRepository.getNumberOfOrders();
        assertEquals(2, numberOfOrders);
    }

    private Order getOrderObjectFromDatabaseById(long id) {
        Order order = new Order();
        Map<Pizza, Integer> pizzas = new HashMap<>();
        List<Map<String, Object>> selectedPizzas = jdbcTemplate.queryForList("SELECT pizza_id, quantity FROM pizzas_quantities WHERE order_id = ?", id);
        for (Map<String, Object> map : selectedPizzas) {
            Pizza pizza = (Pizza) jdbcTemplate.queryForObject("SELECT * FROM pizzas WHERE id = ?", new Object[]{map.get("PIZZA_ID")},
                    new BeanPropertyRowMapper(Pizza.class));
            pizzas.put(pizza, (Integer) map.get("QUANTITY"));
        }
        Customer c = customerRepository.findById(1L);
        Customer customer = (Customer) jdbcTemplate.queryForObject("SELECT * FROM customers JOIN orders ON customer_id = customers.id WHERE orders.id = ?", new Object[]{id}, new BeanPropertyRowMapper(Customer.class));
        AccumulativeCard accumulativeCard = (AccumulativeCard) jdbcTemplate.queryForObject("SELECT * FROM accumulative_cards JOIN customers ON accumulative_card_id = customers.id WHERE customers.id IN (SELECT customers.id FROM customers JOIN orders ON customer_id = customers.id WHERE orders.id = ?)", new Object[]{id}, new BeanPropertyRowMapper(AccumulativeCard.class));
        Address address = (Address) jdbcTemplate.queryForObject("SELECT * FROM addresses JOIN customers ON address_id = customers.id WHERE customers.id IN (SELECT customers.id FROM customers JOIN orders ON customer_id = customers.id WHERE orders.id = ?)", new Object[]{id}, new BeanPropertyRowMapper(Address.class));
        customer.setAccumulativeCard(accumulativeCard);
        customer.setAddress(address);

        address = (Address) jdbcTemplate.queryForObject("SELECT * FROM addresses JOIN orders ON addresses.id = address_id WHERE orders.id = ?", new Object[]{id}, new BeanPropertyRowMapper(Address.class));

        try {
            Payment payment = (Payment) jdbcTemplate.queryForObject("SELECT * FROM payments JOIN orders ON payments.id = payment_id WHERE orders.id = ?", new Object[]{id}, new BeanPropertyRowMapper(Payment.class));
            Discount discount = (Discount) jdbcTemplate.queryForObject("SELECT * FROM discounts JOIN payments ON applied_discount = discounts.name WHERE payments.id IN (SELECT payments.id FROM payments JOIN orders ON payment_id = payments.id WHERE orders.id = ?)", new Object[]{id}, new BeanPropertyRowMapper(Discount.class));
            payment.setAppliedDiscount(discount);
            order.setPayment(payment);
        }catch(EmptyResultDataAccessException ex){
            order.setPayment(null);
        }

        order.setPizzas(pizzas);
        order.setCustomer(customer);
        order.setAddress(address);

        String status = jdbcTemplate.queryForObject("SELECT status FROM orders WHERE id = ?", new Object[]{id}, String.class);
        order.setStatus(Order.OrderStatus.valueOf(status));
        ///order.setStatus(Order.OrderStatus.valueOf(status));
        return order;
    }
}