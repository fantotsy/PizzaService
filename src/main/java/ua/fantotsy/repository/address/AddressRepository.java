package ua.fantotsy.repository.address;

import ua.fantotsy.domain.Address;

public interface AddressRepository {

    Address getAddressById(long id);

    void addNewAddress(Address customer);
}