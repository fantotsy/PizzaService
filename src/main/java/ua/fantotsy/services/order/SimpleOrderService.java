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
    public Order placeNewOrder(Customer customer, Integer... pizzasId) {
        if (!isAllowedAmountOfPizzas(pizzasId)) {
            throw new RuntimeException("Not allowed amount of pizzas!");
        } else {
            List<Pizza> pizzas = new ArrayList<>();
            for (int id : pizzasId) {
                pizzas.add(getPizzaById(id));
            }
            Order newOrder = createNewOrder();
            newOrder.setCustomer(customer);
            newOrder.setPizzas(pizzas);
            newOrder.countTotalPrice();
            orderRepository.saveOrder(newOrder);
            return newOrder;
        }
    }

    @Override
    public void addPizzaInOrderById(long orderId, long pizzaId) {
        Pizza pizza = getPizzaById(pizzaId);
        orderRepository.addPizzaInOrderById(orderId, pizza);
    }

    @Override
    public void removePizzaFromOrderById(long orderId, long pizzaId) {
        Pizza pizza = getPizzaById(pizzaId);
        orderRepository.addPizzaInOrderById(orderId, pizza);
    }

    @Override
    public Pizza getPizzaById(long id) {
        return pizzaService.getPizzaById(id);
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerService.getCustomerById(id);
    }

    @Override
    public void saveOrder(Order newOrder) {
        orderRepository.saveOrder(newOrder);
    }

    @Override
    public int getNumberOfOrders() {
        return orderRepository.getNumberOfOrders();
    }

    @Override
    public void addNewPizza(String name, double price, Pizza.PizzaTypes type) {
        pizzaService.addNewPizza(name, price, type);
    }

    @Override
    public void addNewCustomer(String name, String city, String street, boolean hasAccumulativeCard) {
        customerService.addNewCustomer(name, city, street, hasAccumulativeCard);
    }

    @Override
    public void payOrderById(long id) {
        orderRepository.payOrderById(id);
    }

    @Override
    public void cancelOrderById(long id) {
        orderRepository.cancelOrderById(id);
    }

    @Override
    public double getTotalOrderPriceById(long id) {
        return orderRepository.getOrderById(id).getTotalPrice();
    }

    /*Private & Protected Methods*/
    protected Order createNewOrder() {
        throw new IllegalStateException();
    }

    private boolean isAllowedAmountOfPizzas(Integer... pizzasId) {
        return ((pizzasId.length >= 1) && (pizzasId.length <= 10));
    }
}