package ua.fantotsy.repository.order;

import ua.fantotsy.domain.Order;

import java.util.List;

public interface OrderRepository {

    Order saveOrder(Order order);

    Order getOrderById(long id);

    int getNumberOfOrders();
}