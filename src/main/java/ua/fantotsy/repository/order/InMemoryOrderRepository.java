package ua.fantotsy.repository.order;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Order;
import ua.fantotsy.infrastructure.annotations.Benchmark;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    /*Fields*/
    private final List<Order> orders;

    /*Constructors*/
    public InMemoryOrderRepository() {
        orders = new ArrayList<>();
    }

    /*Public Methods*/
    @Benchmark(value = false)
    @Override
    public Order saveOrder(Order order) {
        order.setId(getNextId());
        orders.add(order);
        order.confirm();
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
    public void payOrderById(long id) {
        Order order = getOrderById(id);
        order.pay();
    }

    @Override
    public void cancelOrderById(long id) {
        Order order = getOrderById(id);
        order.cancel();
    }

    @Override
    public int getNumberOfOrders() {
        return orders.size();
    }

    /*Private Methods*/
    private long getNextId() {
        return (orders.size() + 1);
    }
}