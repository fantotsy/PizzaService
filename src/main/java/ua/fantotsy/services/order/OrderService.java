package ua.fantotsy.services.order;

import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

public interface OrderService {

    Order addNewOrder(Customer customer, Long... pizzasId);

    void addPizzaInOrderById(Long orderId, Long pizzaId);

    void removePizzaFromOrderById(Long orderId, Long pizzaId);

    Pizza getPizzaById(Long id);

    Customer getCustomerById(Long id);

    void saveOrder(Order newOrder);

    Integer getNumberOfOrders();

    void addNewPizza(String name, Double price, Pizza.PizzaType type);

    void addNewCustomer(String name, String city, String street, Boolean hasAccumulativeCard);

    void payOrderById(Long id);

    void cancelOrderById(Long id);

    Double getTotalOrderPriceById(Long id);
}