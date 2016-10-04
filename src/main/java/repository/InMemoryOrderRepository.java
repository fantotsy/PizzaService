package repository;

import domain.Order;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {
    private List<Order> orders;

    public InMemoryOrderRepository() {
        orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public void saveOrder(Order order) {
        orders.add(order);
    }
}