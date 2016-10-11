package ua.fantotsy.services.order;

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

    Set<Discount> getDiscounts();

    Set<Discount> getActiveDiscounts();

    Discount getDiscountByName(String name);

    void saveOrder(Order newOrder);

    int getNumberOfOrders();

    void addNewPizza(Pizza newPizza);

    void addNewCustomer(Customer newCustomer);

    void addNewDiscount(Discount discount);

    void payOrderById(long id);

    void cancelOrderById(long id);

    double getTotalOrderPriceById(long id);
}