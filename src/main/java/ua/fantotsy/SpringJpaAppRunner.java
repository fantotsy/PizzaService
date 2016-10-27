package ua.fantotsy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.customer.CustomerService;
import ua.fantotsy.services.order.OrderService;

import java.util.Arrays;

public class SpringJpaAppRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        System.out.println(Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, repoContext);
        System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));

        OrderService orderService = appContext.getBean(OrderService.class, "orderService");

        orderService.addNewCustomer("Vasya", "Kyiv", "K18", true);
        orderService.addNewCustomer("Petya", "Kyiv", "K18", false);
        orderService.addNewCustomer("Katya", "Kyiv", "K18a", true);

        orderService.addNewPizza("Diabola", 100.5, Pizza.PizzaType.MEAT);
        orderService.addNewPizza("Hawaii", 100.0, Pizza.PizzaType.SEA);
        orderService.addNewPizza("Neapolitana", 90.0, Pizza.PizzaType.VEGETARIAN);

        orderService.addNewOrderByCustomerIdAndPizzaIds(3L, 9L, 10L, 10L, 11L);
        orderService.addNewOrderByCustomerIdAndPizzaIds(5L, 9L, 11L);
        orderService.addNewOrderByCustomerIdAndPizzaIds(8L, 10L);

        orderService.confirmOrderById(13L);
        orderService.payOrderById(13L);

        CustomerService customerService = appContext.getBean(CustomerService.class, "customerService");
        System.out.println(customerService.findCustomerByName("Vasya"));

        repoContext.close();
        appContext.close();
    }
}