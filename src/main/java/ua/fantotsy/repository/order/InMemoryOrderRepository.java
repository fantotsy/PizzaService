package ua.fantotsy.repository.order;

import ua.fantotsy.domain.Order;
import ua.fantotsy.infrastructure.annotations.BenchMark;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {
    private final List<Order> orders;

    public InMemoryOrderRepository() {
        orders = new ArrayList<>();
    }

    @BenchMark(value = false)
    @Override
    public Order saveOrder(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public int getNumberOfOrders() {
        return orders.size();
    }
}