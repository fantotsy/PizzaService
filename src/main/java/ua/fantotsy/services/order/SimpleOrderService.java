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
    public Order placeNewOrder(Customer customer, Integer... pizzasId) {
        if (!isAllowedAmountOfPizzas(pizzasId)) {
            throw new RuntimeException("Not allowed amount of pizzas!");
        } else {
            List<Pizza> pizzas = new ArrayList<>();

            for (int id : pizzasId) {
                pizzas.add(getPizzaById(id));
            }
            Order newOrder = new Order(customer, pizzas);

            orderRepository.saveOrder(newOrder);
            return newOrder;
        }
    }

    @Override
    public Pizza getPizzaById(Integer id) {
        return pizzaService.getPizzaById(id);
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
    public void addNewPizza(Pizza newPizza) {
        pizzaService.addNewPizza(newPizza);
    }

    @Override
    public double getTotalOrderPriceById(long id) {
        return orderRepository.getOrderById(id).getTotalPrice();
    }

    private boolean isAllowedAmountOfPizzas(Integer... pizzasId) {
        return ((pizzasId.length >= 1) && (pizzasId.length <= 10));
    }
}