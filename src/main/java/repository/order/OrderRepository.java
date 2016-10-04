package repository.order;

import domain.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> getOrders();

    Order saveOrder(Order order);
}