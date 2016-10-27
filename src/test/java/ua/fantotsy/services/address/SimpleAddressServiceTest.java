package ua.fantotsy.services.address;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.fantotsy.domain.Address;
import ua.fantotsy.repository.address.AddressRepository;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAddressServiceTest {
    SimpleAddressService addressService;
    @Mock
    AddressRepository addressRepositoryMock;
    @Mock
    Address addressMock;

    @Before
    public void setUp() {
        addressService = spy(new SimpleAddressService(addressRepositoryMock));
        doReturn(addressMock).when(addressService).createNewAddress();
    }

    @Test
    public void testAddAndReturnNewAddressAddsAddress() {
        addressService.addNewAddress("City", "Street");
        verify(addressRepositoryMock).save(addressMock);
    }
}