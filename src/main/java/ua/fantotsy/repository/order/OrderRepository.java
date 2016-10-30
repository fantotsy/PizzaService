package ua.fantotsy.repository.order;

import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

import java.util.List;

public interface OrderRepository {

    Order findById(Long id);

    List<Order> findOrdersByCustomerName(String customerName);

    long getAmountOfPizzasByOrderId(Long orderId);

    Pizza getPizzaByIdInOrderById(Long orderId, Long pizzaId);

    Order save(Order order);

    Order confirmById(Long id);

    Order payById(Long id);

    Order cancelById(Long id);

    Order addPizzaByOrderId(Long orderId, Pizza pizza);

    Order removePizzaByOrderId(Long orderId, Pizza pizza);

    long getNumberOfOrders();
}