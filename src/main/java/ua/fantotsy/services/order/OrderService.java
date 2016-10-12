package ua.fantotsy.services.order;

import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.Set;

public interface OrderService {

    Order placeNewOrder(Customer customer, Integer... pizzasId);

    Pizza getPizzaById(long id);

    Customer getCustomerById(long id);

    void saveOrder(Order newOrder);

    int getNumberOfOrders();

    void addNewPizza(String name, double price, Pizza.PizzaTypes type);

    void addNewCustomer(String name, String city, String street, boolean hasAccumulativeCard);

    void payOrderById(long id);

    void cancelOrderById(long id);

    double getTotalOrderPriceById(long id);
}