import domain.Customer;
import domain.Pizza;
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
        orderService = new SimpleOrderService();
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
}