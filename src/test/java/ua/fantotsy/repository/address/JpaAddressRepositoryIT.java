package ua.fantotsy.repository.address;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.Address;
import ua.fantotsy.repository.RepositoryTestConfig;

import static org.junit.Assert.assertNotNull;

public class JpaAddressRepositoryIT extends RepositoryTestConfig {
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testFind() {

    }

    @Test
    public void testSavePizza() {
        Address address = new Address("City", "Street");
        address = addressRepository.save(address);
        assertNotNull(address.getId());
    }
}