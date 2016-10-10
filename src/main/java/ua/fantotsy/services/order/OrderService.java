package ua.fantotsy.services.order;

import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.pizza.PizzaService;

public interface OrderService {

    Order placeNewOrder(Customer customer, Integer... pizzasId);

    Pizza getPizzaById(long id);

    Customer getCustomerById(long id);

    void saveOrder(Order newOrder);

    int getNumberOfOrders();

    void addNewPizza(Pizza newPizza);

    void addNewCustomer(Customer newCustomer);

    void payOrderById(long id);

    void cancelOrderById(long id);

    double getTotalOrderPriceById(long id);
}