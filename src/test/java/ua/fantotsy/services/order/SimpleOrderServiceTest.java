package ua.fantotsy.services.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.customer.SimpleCustomerService;
import ua.fantotsy.services.pizza.SimplePizzaService;

import static org.mockito.Mockito.*;

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
    }

    @Test(expected = RuntimeException.class)
    public void testPlaceNewOrderTooMuchPizzas() {
        orderService.addNewOrderByCustomerIdAndPizzaIds(customerMock.getId(), 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L);
    }

    @Test(expected = RuntimeException.class)
    public void testPlaceNewOrderNotEnoughPizzas() {
        orderService.addNewOrderByCustomerIdAndPizzaIds(customerMock.getId());
    }

    @Test
    public void testPlaceNewOrderSearchesForPizza() {
        doReturn(orderMock).when(orderService).createNewOrder();
        orderService.addNewOrderByCustomerIdAndPizzaIds(customerMock.getId(), 1L, 1L, 1L);
        verify(pizzaServiceMock, times(3)).findById(1L);
    }

    @Test
    public void testPlaceNewOrderSavesOrder() {
        doReturn(orderMock).when(orderService).createNewOrder();
        orderService.addNewOrderByCustomerIdAndPizzaIds(customerMock.getId(), 1L, 1L, 1L);
        verify(orderRepositoryMock).save(orderMock);
    }
}