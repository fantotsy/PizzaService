package ua.fantotsy.services.address;

import ua.fantotsy.domain.Address;

public interface AddressService {

    Address getAddressById(long id);

    Address addAndReturnNewAddress(String city, String street);
}