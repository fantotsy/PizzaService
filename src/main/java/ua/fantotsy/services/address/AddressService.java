package ua.fantotsy.services.address;

import ua.fantotsy.domain.Address;

public interface AddressService {

    Address getAddressById(Long id);

    Address addNewAddress(String city, String street);
}