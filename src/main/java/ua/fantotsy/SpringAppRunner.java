package ua.fantotsy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.customer.CustomerService;
import ua.fantotsy.services.order.OrderService;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.Arrays;

public class SpringAppRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, repoContext);
        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));

        OrderService orderService = appContext.getBean(OrderService.class, "orderService");
        PizzaService pizzaService = appContext.getBean(PizzaService.class, "pizzaService");
        CustomerService customerService = appContext.getBean(CustomerService.class, "customerService");

        pizzaService.addNewPizza("Diabola", 300.0, Pizza.PizzaType.MEAT);
        customerService.addNewCustomer("Vasya", "Kyiv", "K18a", true);

        Order order1 = orderService.addNewOrderByCustomerIdAndPizzaIds(1L, 1L, 1L, 1L, 1L, 1L);
        order1.pay();
        Order order2 = orderService.addNewOrderByCustomerIdAndPizzaIds(1L, 1L, 1L);
        System.out.println("TOTAL: " + order1.getTotalPrice());
        System.out.println("TOTAL: " + order2.getTotalPrice());

        System.out.println(order1);
        System.out.println(order2);

        repoContext.close();
        appContext.close();
    }
}