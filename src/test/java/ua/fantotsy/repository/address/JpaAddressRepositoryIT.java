package ua.fantotsy.repository.address;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.Address;
import ua.fantotsy.repository.RepositoryTestConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JpaAddressRepositoryIT extends RepositoryTestConfig {
    @Autowired
    private AddressRepository addressRepository;

    @After
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM addresses");
    }

    @Test
    public void testFindAddressById() {
        jdbcTemplate.update("INSERT INTO addresses (id, city, street) VALUES (1, 'City', 'Street')");
        Address address = addressRepository.findById(1L);
        long id = address.getAddressId();
        assertEquals(1L, id);
    }

    @Test
    public void testSetIdAfterSave() {
        Address address = new Address("City", "Street");
        address = addressRepository.save(address);
        assertNotNull(address.getAddressId());
    }
}