import domain.Customer;
import domain.Order;
import services.order.SimpleOrderService;
import services.pizza.PizzaService;
import services.pizza.SimplePizzaService;

import java.util.ArrayList;
import java.util.List;

public class AppRunner {
    public static void main(String[] args) {
        System.out.println("domain.Pizza Service");
//        Customer customer = new Customer(1L, "Vasya");
//        Order order;
//        SimpleOrderService simpleOrderService = new SimpleOrderService();
//        order = simpleOrderService.placeNewOrder(customer, 1, 2, 3);
//        System.out.println(order);

        PizzaService pizzaService = new SimplePizzaService();
        System.out.println(pizzaService.getPizzaById(1));
    }
}