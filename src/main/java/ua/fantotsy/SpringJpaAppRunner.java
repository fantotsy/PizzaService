package ua.fantotsy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.customer.CustomerService;
import ua.fantotsy.services.order.OrderService;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.Arrays;

public class SpringJpaAppRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, repoContext);
        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));


        OrderService orderService = appContext.getBean(OrderService.class, "orderService");
        PizzaService pizzaService = appContext.getBean(PizzaService.class, "pizzaService");
        CustomerService customerService = appContext.getBean(CustomerService.class, "customerService");


        customerService.addNewCustomer("Vasya", "Kyiv", "K18", true);
        customerService.addNewCustomer("Petya", "Kyiv", "K18", false);
        customerService.addNewCustomer("Katya", "Kyiv", "K18a", true);

        pizzaService.addNewPizza("Diabola", 100.5, Pizza.PizzaType.MEAT);
        pizzaService.addNewPizza("Hawaii", 100.0, Pizza.PizzaType.SEA);
        pizzaService.addNewPizza("Neapolitana", 90.0, Pizza.PizzaType.VEGETARIAN);

        orderService.addNewOrderByCustomerIdAndPizzaIds(3L, 9L, 10L, 10L, 11L);
        orderService.addNewOrderByCustomerIdAndPizzaIds(3L, 9L, 11L);
        orderService.addNewOrderByCustomerIdAndPizzaIds(8L, 10L);

        orderService.confirmOrderById(12L);
        orderService.payOrderById(12L);
        orderService.confirmOrderById(13L);
        orderService.payOrderById(13L);
        orderService.cancelOrderById(14L);

        System.out.println(customerService.findCustomerByName("Vasya"));

        System.out.println(orderService.findOrdersByCustomerName("Vasya"));

        repoContext.close();
        appContext.close();
    }
}