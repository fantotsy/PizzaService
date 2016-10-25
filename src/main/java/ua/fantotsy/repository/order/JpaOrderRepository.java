package ua.fantotsy.repository.order;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("orderRepository")
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    @Transactional
    public Order save(Order order) {
        return entityManager.merge(order);
    }

    @Override
    @Transactional
    public Order confirmById(Long id) {
        Order order = entityManager.find(Order.class, id);
        order.confirm();
        return order;
    }

    @Override
    @Transactional
    public Order payById(Long id) {
        Order order = entityManager.find(Order.class, id);
        order.pay();
        return order;
    }

    @Override
    @Transactional
    public Order cancelById(Long id) {
        Order order = entityManager.find(Order.class, id);
        order.cancel();
        return order;
    }

    @Override
    @Transactional
    public Order addPizzaByOrderId(Long orderId, Pizza pizza) {
        Order order = entityManager.find(Order.class, orderId);
        order.addPizza(pizza);
        return order;
    }

    @Override
    @Transactional
    public Order removePizzaByOrderId(Long orderId, Pizza pizza) {
        Order order = entityManager.find(Order.class, orderId);
        order.removePizza(pizza);
        return order;
    }

    @Override
    public int getNumberOfOrders() {
        return 0;
    }
}