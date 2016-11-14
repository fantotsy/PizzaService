package ua.fantotsy.repository.order;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("orderRepository")
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> findOrdersByCustomerName(String customerName) {
        return entityManager.createNamedQuery("Order.findOrdersByCustomerName", Order.class)
                .setParameter("customerName", customerName).getResultList();
    }

    @Override
    public long getAmountOfPizzasByOrderId(Long orderId) {
        Order order = findById(orderId);
        return order.amountOfPizzas();
    }

    @Override
    public Pizza getPizzaByIdInOrderById(Long orderId, Long pizzaId) {
        Order order = findById(orderId);
        return order.pizzaById(pizzaId);
    }

    @Override
    public Order save(Order order) {
        return entityManager.merge(order);
    }

    @Override
    public Order confirmById(Long id) {
        Order order = entityManager.find(Order.class, id);
        order.confirm();
        return order;
    }

    @Override
    public Order payById(Long id) {
        Order order = entityManager.find(Order.class, id);
        order.pay();
        return order;
    }

    @Override
    public Order cancelById(Long id) {
        Order order = entityManager.find(Order.class, id);
        order.cancel();
        return order;
    }

    @Override
    public Order addPizzaByOrderId(Long orderId, Pizza pizza) {
        Order order = entityManager.find(Order.class, orderId);
        order.addPizza(pizza);
        return order;
    }

    @Override
    public Order removePizzaByOrderId(Long orderId, Pizza pizza) {
        Order order = entityManager.find(Order.class, orderId);
        order.removePizza(pizza);
        return order;
    }

    @Override
    public long getNumberOfOrders() {
        return entityManager.createNamedQuery("Order.getNumberOfOrders", Long.class).getSingleResult();
    }
}