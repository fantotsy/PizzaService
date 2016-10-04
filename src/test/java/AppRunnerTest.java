import domain.Customer;
import domain.Pizza;
import infrastructure.ApplicationContext;
import infrastructure.Context;
import infrastructure.JavaConfig;
import org.junit.Before;
import org.junit.Test;
import services.order.OrderService;
import services.order.SimpleOrderService;

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