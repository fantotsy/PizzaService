package domain;

import org.junit.Before;
import org.junit.Test;
import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.config.JavaConfig;
import ua.fantotsy.infrastructure.context.ApplicationContext;
import ua.fantotsy.infrastructure.context.Context;
import ua.fantotsy.services.order.OrderService;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    private OrderService orderService;
    private final double eps = 0.00001;

    @Before
    public void setUp() {
        Context context = new ApplicationContext(new JavaConfig());
//        orderService = context.getBean("orderService");
//        orderService.addNewPizza(new Pizza("First", 100.0, Pizza.PizzaTypes.VEGETARIAN));
//        orderService.addNewCustomer(new Customer("Vasya", new Address("Kyiv", "Kudryashova"), true));
//        orderService.addNewCustomer(new Customer("Petya", new Address("Kyiv", "Kudryashova"), false));
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