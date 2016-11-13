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
    public List<Order> findOrdersByCustomerName(String customerName) {
        return null;
    }

    @Override
    public long getAmountOfPizzasByOrderId(Long orderId) {
        Order order = findById(orderId);
        return order.getAmountOfPizzas();
    }

    @Override
    public Pizza getPizzaByIdInOrderById(Long orderId, Long pizzaId) {
        Order order = findById(orderId);
        return order.getPizzaById(pizzaId);
    }

    @Benchmark(false)
    @Override
    public Order save(Order order) {
        order.setOrderId(getNextId());
        orders.add(order);
        order.confirm();
        return order;
    }

    @Override
    public Order confirmById(Long id) {
        Order order = findById(id);
        order.confirm();
        return order;
    }

    @Override
    public Order payById(Long id) {
        Order order = findById(id);
        order.pay();
        return order;
    }

    @Override
    public Order cancelById(Long id) {
        Order order = findById(id);
        order.cancel();
        return order;
    }

    @Override
    public Order addPizzaByOrderId(Long orderId, Pizza pizza) {
        Order order = findById(orderId);
        order.addPizza(pizza);
        return order;
    }

    @Override
    public Order removePizzaByOrderId(Long orderId, Pizza pizza) {
        Order order = findById(orderId);
        order.removePizza(pizza);
        return order;
    }

    @Override
    public long getNumberOfOrders() {
        return orders.size();
    }

    /*Private Methods*/
    private long getNextId() {
        return (orders.size() + 1);
    }
}