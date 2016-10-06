import ua.fantotsy.domain.Customer;
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

    @Test
    public void testPlaceNewOrder() {
        orderService.placeNewOrder(new Customer(), 1);
        assertEquals(1, orderService.getInMemoryOrderRepository().getOrders().size());
    }

    @Test
    public void testGetPizzaById() {
        Pizza pizza = new Pizza(5L, "Fifth", 500.0, Pizza.PizzaTypes.Vegetarian);
        orderService.getPizzaService().getPizzaRepository().getPizzas().add(pizza);
        Pizza actual = orderService.getPizzaService().getPizzaRepository().getPizzaById(5);
        assertEquals(pizza, actual);
    }

    @Test
    public void testConvertTypeToBeanName() {
        assertEquals("integer", new ApplicationContext(new JavaConfig()).convertTypeToBeanName(Integer.class));
    }
}