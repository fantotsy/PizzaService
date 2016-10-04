import domain.Customer;
import domain.Pizza;
import org.junit.Before;
import org.junit.Test;
import services.SimpleOrderService;

import static org.junit.Assert.assertEquals;

public class AppRunnerTest {
    private SimpleOrderService simpleOrderService;

    public AppRunnerTest() {

    }

    @Before
    public void setUp() {
        simpleOrderService = new SimpleOrderService();
    }

    @Test
    public void testPlaceNewOrder() {
        simpleOrderService.placeNewOrder(new Customer(), 1);
        assertEquals(1, simpleOrderService.getInMemoryOrderRepository().getOrders().size());
    }

    @Test
    public void testGetPizzaById() {
        Pizza pizza = new Pizza(5L, "Fifth", 500.0, Pizza.PizzaTypes.Vegetarian);
        simpleOrderService.getInMemoryPizzaRepository().getPizzas().add(pizza);
        Pizza actual = simpleOrderService.getInMemoryPizzaRepository().getPizzaById(5);
        assertEquals(pizza, actual);
    }
}