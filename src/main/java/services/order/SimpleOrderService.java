package services.order;

import domain.Customer;
import domain.Order;
import domain.Pizza;
import repository.order.InMemoryOrderRepository;
import repository.order.OrderRepository;
import services.pizza.PizzaService;
import services.pizza.SimplePizzaService;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService {
    private PizzaService pizzaService;
    private OrderRepository inMemoryOrderRepository;

    public SimpleOrderService() {
        pizzaService = new SimplePizzaService();
        inMemoryOrderRepository = new InMemoryOrderRepository();
    }

    public PizzaService getPizzaService() {
        return pizzaService;
    }

    public OrderRepository getInMemoryOrderRepository() {
        return inMemoryOrderRepository;
    }

    @Override
    public Order placeNewOrder(Customer customer, Integer... pizzasId) {
        List<Pizza> pizzas = new ArrayList<>();

        for (int id : pizzasId) {
            pizzas.add(pizzaService.getPizzaById(id));
        }
        Order newOrder = new Order(1L, customer, pizzas);

        inMemoryOrderRepository.saveOrder(newOrder);
        return newOrder;
    }
}