package ua.fantotsy.repository.address;

import ua.fantotsy.domain.Address;

public interface AddressRepository {

    Address findById(Long id);

    Address save(Address address);
}