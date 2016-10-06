package ua.fantotsy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.domain.Order;
import ua.fantotsy.repository.pizza.PizzaRepository;
import ua.fantotsy.services.SomeService;
import ua.fantotsy.services.order.OrderService;

import java.util.Arrays;

public class SpringAppRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, repoContext);
        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));

        System.out.println(repoContext.getBean("T1", SomeService.class).getString());
        System.out.println(appContext.getBean("T1", SomeService.class).getString());

        PizzaRepository pizzaRepository = (PizzaRepository) repoContext.getBean("pizzaRepository");
        System.out.println(pizzaRepository.getPizzaById(1));

        OrderService orderService = (OrderService) appContext.getBean("orderService");
        Order order = orderService.placeNewOrder(null, 1, 2, 3);
        System.out.println(order);

        repoContext.close();
        appContext.close();
    }
}