package ua.fantotsy.services.customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.repository.customer.CustomerRepository;
import ua.fantotsy.services.accumulativeCard.AccumulativeCardService;
import ua.fantotsy.services.address.AddressService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCustomerServiceTest {
    SimpleCustomerService customerService;
    @Mock
    CustomerRepository customerRepositoryMock;
    @Mock
    AddressService addressServiceMock;
    @Mock
    AccumulativeCardService accumulativeCardServiceMock;
    @Mock
    Customer customerMock;
    @Mock
    AccumulativeCard accumulativeCardMock;

    @Before
    public void setUp() {
        customerService = spy(new SimpleCustomerService(customerRepositoryMock, addressServiceMock,
                accumulativeCardServiceMock));
        doReturn(customerMock).when(customerService).createNewCustomer();
    }

    @Test
    public void testAddNewCustomerWithoutAccumulativeCard() {
        customerService.addNewCustomer("Name", "City", "Street", false);
        verify(customerMock, never()).setAccumulativeCard(any());
    }

    @Test
    public void testAddNewCustomerWithAccumulativeCard() {
        when(accumulativeCardServiceMock.addAndReturnNewAccumulativeCard()).thenReturn(accumulativeCardMock);
        customerService.addNewCustomer("Name", "City", "Street", true);
        verify(customerMock).setAccumulativeCard(accumulativeCardMock);
    }

    @Test
    public void testAddNewCustomerAddsCustomerWithAccumulativeCard() {
        customerService.addNewCustomer("Name", "City", "Street", true);
        verify(customerRepositoryMock).save(customerMock);
    }

    @Test
    public void testAddNewCustomerAddsCustomerWithoutAccumulativeCard() {
        customerService.addNewCustomer("Name", "City", "Street", false);
        verify(customerRepositoryMock).save(customerMock);
    }
}