package services;

import domain.Customer;
import domain.Order;
import domain.Pizza;
import repository.InMemoryOrderRepository;
import repository.InMemoryPizzaRepository;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService {
    private InMemoryPizzaRepository inMemoryPizzaRepository;
    private InMemoryOrderRepository inMemoryOrderRepository;

    public InMemoryPizzaRepository getInMemoryPizzaRepository() {
        return inMemoryPizzaRepository;
    }

    public InMemoryOrderRepository getInMemoryOrderRepository() {
        return inMemoryOrderRepository;
    }

    public SimpleOrderService() {
        inMemoryPizzaRepository = new InMemoryPizzaRepository();
        inMemoryOrderRepository = new InMemoryOrderRepository();
    }

    public Order placeNewOrder(Customer customer, Integer... pizzasId) {
        List<Pizza> pizzas = new ArrayList<>();

        for (int id : pizzasId) {
            pizzas.add(inMemoryPizzaRepository.getPizzaById(id));
        }
        Order newOrder = new Order(1L, customer, pizzas);

        inMemoryOrderRepository.saveOrder(newOrder);
        return newOrder;
    }
}