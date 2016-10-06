package ua.fantotsy.services.order;

import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.pizza.PizzaService;

public interface OrderService {
    OrderRepository getInMemoryOrderRepository();

    PizzaService getPizzaService();

    Order placeNewOrder(Customer customer, Integer... pizzasId);
}