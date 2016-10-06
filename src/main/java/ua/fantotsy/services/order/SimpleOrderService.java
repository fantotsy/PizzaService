package ua.fantotsy.services.order;

import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;

    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
    }

    @Override
    public OrderRepository getInMemoryOrderRepository() {
        return orderRepository;
    }

    @Override
    public PizzaService getPizzaService() {
        return pizzaService;
    }

    @Override
    public Order placeNewOrder(Customer customer, Integer... pizzasId) {
        List<Pizza> pizzas = new ArrayList<>();

        for (int id : pizzasId) {
            pizzas.add(pizzaService.getPizzaById(id));
        }
        Order newOrder = new Order(1L, customer, pizzas);

        orderRepository.saveOrder(newOrder);
        return newOrder;
    }
}