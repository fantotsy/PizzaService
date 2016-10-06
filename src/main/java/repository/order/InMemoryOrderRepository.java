package repository.order;

import domain.Order;
import infrastructure.annotations.BenchMark;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {
    private final List<Order> orders;

    public InMemoryOrderRepository() {
        orders = new ArrayList<>();
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @BenchMark(value = true)
    @Override
    public Order saveOrder(Order order) {
        orders.add(order);
        return order;
    }
}