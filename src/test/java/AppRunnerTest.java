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
    private double eps = 0.00001;

    public AppRunnerTest() {

    }

    @Before
    public void setUp() {
        Context context = new ApplicationContext(new JavaConfig());
        orderService = context.getBean("orderService");
    }

    @Test(expected = RuntimeException.class)
    public void testTooMuchPizzasInOrder() {
        orderService.addNewPizza(new Pizza("First", 100.0, Pizza.PizzaTypes.Vegetarian));
        orderService.placeNewOrder(null, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    }

    @Test(expected = RuntimeException.class)
    public void testNotEnoughPizzasInOrder() {
        orderService.placeNewOrder(null);
    }

    @Test
    public void testAllowedAmountOfPizzasInOrder() {
        orderService.addNewPizza(new Pizza("First", 100.0, Pizza.PizzaTypes.Vegetarian));
        orderService.placeNewOrder(null, 1);
        assertEquals(1, orderService.getNumberOfOrders());
    }

    @Test
    public void testGetPizzaById() {
        Pizza pizza = new Pizza("First", 500.0, Pizza.PizzaTypes.Vegetarian);
        orderService.addNewPizza(pizza);
        Pizza actual = orderService.getPizzaById(1);
        assertEquals(pizza, actual);
    }

    @Test
    public void testGetTotalOrderPriceWithoutDiscount(){
        orderService.addNewPizza(new Pizza("First", 100.0, Pizza.PizzaTypes.Vegetarian));
        orderService.placeNewOrder(null, 1);
        assertEquals(100.0, orderService.getTotalOrderPriceById(1L), eps);
    }

    @Test
    public void testGetTotalOrderPriceWithDiscount(){
        orderService.addNewPizza(new Pizza("First", 100.0, Pizza.PizzaTypes.Vegetarian));
        orderService.placeNewOrder(null, 1, 1, 1, 1, 1);
        assertEquals(350.0, orderService.getTotalOrderPriceById(1L), eps);
    }

    @Test
    public void testConvertTypeToBeanName() {
        assertEquals("integer", new ApplicationContext(new JavaConfig()).convertTypeToBeanName(Integer.class));
    }
}