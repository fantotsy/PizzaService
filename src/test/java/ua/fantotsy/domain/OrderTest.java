package ua.fantotsy.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.fantotsy.services.order.OrderService;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    private final ConfigurableApplicationContext repoContext;
    private final ConfigurableApplicationContext appContext;
    private OrderService orderService;
    private final double eps = 0.00001;

    public OrderTest() {
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

    @Test
    public void testGetTotalOrderPriceWithoutDiscountAndCard() {
        orderService.placeNewOrder(orderService.getCustomerById(2), 1);
        assertEquals(100.0, orderService.getTotalOrderPriceById(1L), eps);
    }

    @Test
    public void testGetTotalOrderPriceWithDiscountWithoutCard() {
        orderService.placeNewOrder(orderService.getCustomerById(2), 1, 1, 1, 1, 1);
        assertEquals(350.0, orderService.getTotalOrderPriceById(1L), eps);
    }

    @Test
    public void testGetTotalOrderPriceWithoutDiscountWithCard() {
        orderService.placeNewOrder(orderService.getCustomerById(1), 1);
        orderService.payOrderById(1L);
        assertEquals(100.0, orderService.getTotalOrderPriceById(1L), eps);
        orderService.placeNewOrder(orderService.getCustomerById(1), 1);
        orderService.payOrderById(2L);
        assertEquals(90.0, orderService.getTotalOrderPriceById(2L), eps);
        orderService.placeNewOrder(orderService.getCustomerById(1), 1);
        orderService.payOrderById(3L);
        assertEquals(81.0, orderService.getTotalOrderPriceById(3L), eps);
    }

    @Test
    public void testGetTotalOrderPriceWithDiscountWithCard() {
        orderService.placeNewOrder(orderService.getCustomerById(2), 1, 1, 1, 1, 1);
        assertEquals(350.0, orderService.getTotalOrderPriceById(1L), eps);
    }
}