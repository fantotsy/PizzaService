package services.order;

import domain.Customer;
import domain.Order;
import repository.order.OrderRepository;
import services.pizza.PizzaService;

public interface OrderService {
    OrderRepository getInMemoryOrderRepository();

    PizzaService getPizzaService();

    Order placeNewOrder(Customer customer, Integer... pizzasId);
}