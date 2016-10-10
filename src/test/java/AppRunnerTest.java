import org.junit.Before;
import ua.fantotsy.domain.Address;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.context.ApplicationContext;
import ua.fantotsy.infrastructure.context.Context;
import ua.fantotsy.infrastructure.config.JavaConfig;
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
        orderService.addNewPizza(new Pizza("First", 100.0, Pizza.PizzaTypes.Vegetarian));
        orderService.addNewCustomer(new Customer("Vasya", new Address("Kyiv", "Kudryashova"), true));
        orderService.addNewCustomer(new Customer("Petya", new Address("Kyiv", "Kudryashova"), false));
    }

    @Test(expected = RuntimeException.class)
    public void testTooMuchPizzasInOrder() {
        orderService.addNewPizza(new Pizza("First", 100.0, Pizza.PizzaTypes.Vegetarian));
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

    @Test
    public void testGetPizzaById() {
        Pizza pizza = new Pizza("Second", 200.0, Pizza.PizzaTypes.Vegetarian);
        orderService.addNewPizza(pizza);
        Pizza actual = orderService.getPizzaById(2);
        assertEquals(pizza, actual);
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
        orderService.placeNewOrder(orderService.getCustomerById(1), 1);
        assertEquals(90.0, orderService.getTotalOrderPriceById(2L), eps);
    }

    @Test
    public void testGetTotalOrderPriceWithDiscountWithCard() {
        orderService.placeNewOrder(orderService.getCustomerById(2), 1, 1, 1, 1, 1);
        assertEquals(350.0, orderService.getTotalOrderPriceById(1L), eps);
    }

    @Test
    public void testConvertTypeToBeanName() {
        assertEquals("integer", new ApplicationContext(new JavaConfig()).convertTypeToBeanName(Integer.class));
    }
}