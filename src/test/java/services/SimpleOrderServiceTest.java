package services;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.order.OrderService;

import static org.junit.Assert.assertEquals;

public class SimpleOrderServiceTest {
    private final ConfigurableApplicationContext repoContext;
    private final ConfigurableApplicationContext appContext;
    private OrderService orderService;

    public SimpleOrderServiceTest() {
        repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
        appContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"}, repoContext);
    }

    @Before
    public void setUp() {
        orderService = (OrderService) appContext.getBean("orderService");
        orderService.addNewPizza("Diabola", 100.0, Pizza.PizzaTypes.MEAT);
        orderService.addNewCustomer("Vasya", "Kyiv", "K18a", true);
        orderService.addNewCustomer("Vasya", "Kyiv", "K18a", false);
    }

    @Test(expected = RuntimeException.class)
    public void testTooMuchPizzasInOrder() {
        orderService.placeNewOrder(orderService.getCustomerById(1), 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    }

    @Test(expected = RuntimeException.class)
    public void testNotEnoughPizzasInOrder() {
        orderService.placeNewOrder(orderService.getCustomerById(1));
    }

    @Test
    public void testAllowedAmountOfPizzasInOrder() {
        orderService.placeNewOrder(orderService.getCustomerById(1), 1);
        assertEquals(1, orderService.getNumberOfOrders());
    }
}