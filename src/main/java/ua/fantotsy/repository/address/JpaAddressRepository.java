package ua.fantotsy.repository.address;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.fantotsy.domain.Address;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("addressRepository")
public class JpaAddressRepository implements AddressRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Address findById(Long id) {
        return entityManager.find(Address.class, id);
    }

    @Override
    public Address save(Address address) {
        return entityManager.merge(address);
    }
}