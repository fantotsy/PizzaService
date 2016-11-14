package ua.fantotsy.services.order;

import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

import java.util.List;

public interface OrderService {

    Order findById(Long id);

    Order addNewOrderByCustomerIdAndPizzaIds(Long customerId, Long... pizzasId);

    List<Order> findOrdersByCustomerName(String customerName);

    Order addPizzaByOrderId(Long orderId, Long pizzaId);

    Order removePizzaByOrderId(Long orderId, Long pizzaId);

    long getAmountOfPizzasByOrderId(Long orderId);

    Pizza findPizzaByIdInOrderById(Long orderId, Long pizzaId);

    Order confirmOrderById(Long orderId);

    Order cancelOrderById(Long id);

    Order payOrderById(Long id);

    long getAmountOfOrders();
}