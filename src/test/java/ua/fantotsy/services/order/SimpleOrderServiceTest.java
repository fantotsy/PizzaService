package ua.fantotsy.services.order;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.customer.SimpleCustomerService;
import ua.fantotsy.services.pizza.SimplePizzaService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleOrderServiceTest {
    SimpleOrderService orderService;
    @Mock
    OrderRepository orderRepositoryMock;
    @Mock
    SimplePizzaService pizzaServiceMock;
    @Mock
    SimpleCustomerService customerServiceMock;
    @Mock
    Order orderMock;
    @Mock
    Customer customerMock;

    @Before
    public void setUp() {
        orderService = spy(new SimpleOrderService(orderRepositoryMock, pizzaServiceMock, customerServiceMock));


//        orderService.addNewPizza("Diabola", 100.0, Pizza.PizzaTypes.MEAT);
//        orderService.addNewCustomer("Vasya", "Kyiv", "K18a", true);
//        orderService.addNewCustomer("Vasya", "Kyiv", "K18a", false);
    }

    @Ignore
    @Test(expected = RuntimeException.class)
    public void testTooMuchPizzasInOrder() throws Exception {
        when(orderService.createNewOrder()).thenReturn(orderMock);
        orderService.placeNewOrder(customerMock, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    }

    @Ignore
    @Test(expected = RuntimeException.class)
    public void testNotEnoughPizzasInOrder() {
        orderService.placeNewOrder(orderService.getCustomerById(1));
    }

    @Ignore
    @Test
    public void testAllowedAmountOfPizzasInOrder() {
        orderService.placeNewOrder(orderService.getCustomerById(1), 1);
        assertEquals(1, orderService.getNumberOfOrders());
    }
}