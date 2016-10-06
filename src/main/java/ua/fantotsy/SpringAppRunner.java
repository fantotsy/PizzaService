package ua.fantotsy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.domain.Order;
import ua.fantotsy.repository.pizza.PizzaRepository;
import ua.fantotsy.services.order.OrderService;

import java.util.Arrays;

public class SpringAppRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        PizzaRepository pizzaRepository = (PizzaRepository) context.getBean("pizzaRepository");
        System.out.println(pizzaRepository.getPizzaById(1));
        OrderService orderService = (OrderService) context.getBean("orderService");
        Order order = orderService.placeNewOrder(null, 1, 2, 3);
        System.out.println(order);
        context.close();
    }
}