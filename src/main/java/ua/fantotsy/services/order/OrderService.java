package ua.fantotsy.services.order;

import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

public interface OrderService {

    Order addNewOrderByCustomerIdAndPizzaIds(Long customerId, Long... pizzasId);

    Order addPizzaByOrderId(Long orderId, Long pizzaId);

    Order removePizzaByOrderId(Long orderId, Long pizzaId);

    Integer getAmountOfPizzasByOrderId(Long orderId);

    Pizza findPizzaByIdInOrderById(Long orderId, Long pizzaId);

    Order confirmOrderById(Long orderId);

    Order cancelOrderById(Long id);

    Order payOrderById(Long id);

    Pizza findPizzaById(Long id);

    Customer findCustomerById(Long id);

    Integer getAmountOfOrders();

    void addNewPizza(String name, Double price, Pizza.PizzaType type);

    void addNewCustomer(String name, String city, String street, Boolean hasAccumulativeCard);
}