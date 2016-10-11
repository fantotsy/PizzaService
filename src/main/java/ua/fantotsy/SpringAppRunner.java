package ua.fantotsy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.order.OrderService;

import java.util.Arrays;

public class SpringAppRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, repoContext);
        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));

        OrderService orderService = (OrderService) appContext.getBean("orderService");
        Customer customer = new Customer("Vasya", new Address("Kyiv", "K18a"), true);
        orderService.addNewCustomer(customer);
        orderService.addNewPizza(new Pizza("Diabola", 300.0, Pizza.PizzaTypes.MEAT));
        Order order = orderService.placeNewOrder(customer, 1);
        System.out.println(order);

        repoContext.close();
        appContext.close();
    }
}