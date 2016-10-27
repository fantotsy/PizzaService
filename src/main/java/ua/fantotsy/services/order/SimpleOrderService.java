package ua.fantotsy.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.*;
import ua.fantotsy.infrastructure.annotations.Benchmark;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.customer.CustomerService;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService {
    /*Fields*/
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final CustomerService customerService;

    /*Constructors*/
    @Autowired
    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.customerService = customerService;
    }

    /*Public Methods*/
    @Override
    @Benchmark(value = true)
    public Order addNewOrder(Customer customer, Long... pizzasId) {
        if (!isAllowedAmountOfPizzas(pizzasId)) {
            throw new RuntimeException("Not allowed amount of pizzas!");
        } else {
            List<Pizza> pizzas = new ArrayList<>();
            for (Long id : pizzasId) {
                pizzas.add(getPizzaById(id));
            }
            Order newOrder = createNewOrder();
            newOrder.setCustomer(customer);
            newOrder.insertPizzas(pizzas);
            newOrder.countTotalPrice();
            orderRepository.save(newOrder);
            return newOrder;
        }
    }

    @Override
    public void addPizzaInOrderById(Long orderId, Long pizzaId) {
        Pizza pizza = getPizzaById(pizzaId);
        orderRepository.addPizzaByOrderId(orderId, pizza);
    }

    @Override
    public void removePizzaFromOrderById(Long orderId, Long pizzaId) {
        Pizza pizza = getPizzaById(pizzaId);
        orderRepository.removePizzaByOrderId(orderId, pizza);
    }

    @Override
    public Pizza getPizzaById(Long id) {
        return pizzaService.getPizzaById(id);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerService.getCustomerById(id);
    }

    @Override
    public void saveOrder(Order newOrder) {
        orderRepository.save(newOrder);
    }

    @Override
    public Integer getNumberOfOrders() {
        return orderRepository.getNumberOfOrders();
    }

    @Override
    public void addNewPizza(String name, Double price, Pizza.PizzaType type) {
        pizzaService.addNewPizza(name, price, type);
    }

    @Override
    public void addNewCustomer(String name, String city, String street, Boolean hasAccumulativeCard) {
        customerService.addNewCustomer(name, city, street, hasAccumulativeCard);
    }

    @Override
    public void payOrderById(Long id) {
        orderRepository.payById(id);
    }

    @Override
    public void cancelOrderById(Long id) {
        orderRepository.cancelById(id);
    }

    @Override
    public Double getTotalOrderPriceById(Long id) {
        return orderRepository.findById(id).getTotalPrice();
    }

    /*Private & Protected Methods*/
    protected Order createNewOrder() {
        throw new IllegalStateException();
    }

    private boolean isAllowedAmountOfPizzas(Long... pizzasId) {
        return ((pizzasId.length >= 1) && (pizzasId.length <= 10));
    }
}