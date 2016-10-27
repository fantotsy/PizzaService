package ua.fantotsy.services.address;

import ua.fantotsy.domain.Address;

public interface AddressService {

    Address findAddressById(Long id);

    Address addNewAddress(String city, String street);
}