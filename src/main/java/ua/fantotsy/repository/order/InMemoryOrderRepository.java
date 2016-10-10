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
        order.setId(getNextId());
        orders.add(order);
        return order;
    }

    @Override
    public Order getOrderById(long id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        throw new RuntimeException("Such order id does not exist.");
    }

    @Override
    public int getNumberOfOrders() {
        return orders.size();
    }

    private long getNextId(){
        return (orders.size() + 1);
    }
}