package ua.fantotsy.repository.order;

import ua.fantotsy.domain.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> getOrders();

    Order saveOrder(Order order);
}