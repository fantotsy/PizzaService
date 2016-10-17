package ua.fantotsy.repository.order;

import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

import java.util.List;

public interface OrderRepository {

    Order saveOrder(Order order);

    Order getOrderById(long id);

    void payOrderById(long id);

    void cancelOrderById(long id);

    void addPizzaInOrderById(long orderId, Pizza pizza);

    void removePizzaFromOrderById(long orderId, Pizza pizza);

    int getNumberOfOrders();
}