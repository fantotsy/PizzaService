package ua.fantotsy.repository.order;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
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
    public Order save(Order order) {
        order.setId(getNextId());
        orders.add(order);
        order.confirm();
        return order;
    }

    @Override
    public Order findById(Long id) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        throw new RuntimeException("Such order id does not exist.");
    }

    @Override
    public void payOrderById(Long id) {
        Order order = findById(id);
        order.pay();
    }

    @Override
    public void cancelOrderById(Long id) {
        Order order = findById(id);
        order.cancel();
    }

    @Override
    public void addPizzaInOrderById(Long orderId, Pizza pizza) {
        findById(orderId).addPizza(pizza);
    }

    @Override
    public void removePizzaFromOrderById(Long orderId, Pizza pizza) {
        findById(orderId).removePizza(pizza);
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