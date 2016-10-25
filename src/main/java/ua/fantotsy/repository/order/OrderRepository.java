package ua.fantotsy.repository.order;

import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

public interface OrderRepository {

    Order save(Order order);

    Order findById(Long id);

    void payOrderById(Long id);

    void cancelOrderById(Long id);

    void addPizzaInOrderById(Long orderId, Pizza pizza);

    void removePizzaFromOrderById(Long orderId, Pizza pizza);

    int getNumberOfOrders();
}