package ua.fantotsy.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Benchmark(value = false)
    @Transactional
    public Order addNewOrderByCustomerIdAndPizzaIds(Long customerId, Long... pizzasId) {
        if (!isAllowedAmountOfPizzas(pizzasId)) {
            throw new RuntimeException("Not allowed amount of pizzas!");
        } else {
            List<Pizza> pizzas = new ArrayList<>();
            for (Long id : pizzasId) {
                pizzas.add(pizzaService.findById(id));
            }
            Customer customer = customerService.findById(customerId);
            Order newOrder = createNewOrder();
            newOrder.setCustomer(customer);
            newOrder.insertPizzas(pizzas);
            newOrder = orderRepository.save(newOrder);
            return newOrder;
        }
    }

    @Override
    public List<Order> findOrdersByCustomerName(String customerName) {
        return orderRepository.findOrdersByCustomerName(customerName);
    }

    @Override
    public long getAmountOfPizzasByOrderId(Long orderId) {
        return orderRepository.getAmountOfPizzasByOrderId(orderId);
    }

    @Override
    public Pizza findPizzaByIdInOrderById(Long orderId, Long pizzaId) {
        return orderRepository.getPizzaByIdInOrderById(orderId, pizzaId);
    }

    @Override
    @Transactional
    public Order confirmOrderById(Long orderId) {
        return orderRepository.confirmById(orderId);
    }

    @Override
    @Transactional
    public Order payOrderById(Long id) {
        return orderRepository.payById(id);
    }

    @Override
    @Transactional
    public Order cancelOrderById(Long id) {
        return orderRepository.cancelById(id);
    }

    @Override
    @Transactional
    public Order addPizzaByOrderId(Long orderId, Long pizzaId) {
        Pizza pizza = pizzaService.findById(pizzaId);
        return orderRepository.addPizzaByOrderId(orderId, pizza);
    }

    @Override
    @Transactional
    public Order removePizzaByOrderId(Long orderId, Long pizzaId) {
        Pizza pizza = pizzaService.findById(pizzaId);
        return orderRepository.removePizzaByOrderId(orderId, pizza);
    }

    @Override
    public long getAmountOfOrders() {
        return orderRepository.getNumberOfOrders();
    }

    /*Private & Protected Methods*/
    protected Order createNewOrder() {
        throw new IllegalStateException();
    }

    private boolean isAllowedAmountOfPizzas(Long... pizzasId) {
        return ((pizzasId.length >= 1) && (pizzasId.length <= 10));
    }
}