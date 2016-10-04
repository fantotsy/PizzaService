import domain.Customer;
import domain.Order;
import infrastructure.ApplicationContext;
import infrastructure.Context;
import infrastructure.JavaConfig;
import repository.pizza.PizzaRepository;
import services.order.OrderService;
import services.order.SimpleOrderService;
import services.pizza.PizzaService;
import services.pizza.SimplePizzaService;

import java.util.ArrayList;
import java.util.List;

public class AppRunner {
    public static void main(String[] args) {
        System.out.println("domain.Pizza Service");
        Context context = new ApplicationContext(new JavaConfig());

        PizzaRepository pizzaRepository = context.getBean("pizzaRepository");
        System.out.println(pizzaRepository.getPizzaById(1));

        Customer customer = new Customer();
        OrderService orderService = context.getBean("orderService");
        Order order = orderService.placeNewOrder(customer, 1, 2, 3);
        System.out.println(order);
    }
}