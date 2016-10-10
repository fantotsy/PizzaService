import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.context.ApplicationContext;
import ua.fantotsy.infrastructure.context.Context;
import ua.fantotsy.infrastructure.config.JavaConfig;
import org.junit.Before;
import org.junit.Test;
import ua.fantotsy.services.order.OrderService;

import static org.junit.Assert.assertEquals;

public class AppRunnerTest {
    private OrderService orderService;

    public AppRunnerTest() {

    }

    @Before
    public void setUp() {
        Context context = new ApplicationContext(new JavaConfig());
        orderService = context.getBean("orderService");
    }

    @Test(expected = RuntimeException.class)
    public void testTooMuchPizzasInOrder() {
        orderService.placeNewOrder(null, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
    }

    @Test(expected = RuntimeException.class)
    public void testNotEnoughPizzasInOrder() {
        orderService.placeNewOrder(null);
    }

    @Test
    public void testAllowedAmountOfPizzasInOrder() {
        orderService.placeNewOrder(null, 1);
        assertEquals(1, orderService.getNumberOfOrders());
    }

    @Test
    public void testGetPizzaById() {
        Pizza pizza = new Pizza(5L, "Fifth", 500.0, Pizza.PizzaTypes.Vegetarian);
        orderService.addNewPizza(pizza);
        Pizza actual = orderService.getPizzaById(5);
        assertEquals(pizza, actual);
    }

    @Test
    public void testConvertTypeToBeanName() {
        assertEquals("integer", new ApplicationContext(new JavaConfig()).convertTypeToBeanName(Integer.class));
    }
}