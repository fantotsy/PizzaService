package ua.fantotsy.repository.order;

import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

public interface OrderRepository {

    Order findById(Long id);

    Order save(Order order);

    Order confirmById(Long id);

    Order payById(Long id);

    Order cancelById(Long id);

    Order addPizzaByOrderId(Long orderId, Pizza pizza);

    Order removePizzaByOrderId(Long orderId, Pizza pizza);

    int getNumberOfOrders();
}