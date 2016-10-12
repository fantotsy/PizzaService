package ua.fantotsy.services.address;

import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.Address;
import ua.fantotsy.repository.address.AddressRepository;

public class SimpleAddressService implements AddressService {
    /*Fields*/
    private AddressRepository addressRepository;

    /*Constructors*/
    @Autowired
    public SimpleAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /*Public Methods*/
    @Override
    public Address getAddressById(long id) {
        return addressRepository.getAddressById(id);
    }

    @Override
    public Address addAndReturnNewAddress(String city, String street) {
        Address address = createNewAddress();
        address.setCity(city);
        address.setStreet(street);
        addressRepository.addNewAddress(address);
        return address;
    }

    protected Address createNewAddress() {
        throw new IllegalStateException();
    }
}