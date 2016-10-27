package ua.fantotsy.services.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    public Address getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    @Transactional
    public Address addNewAddress(String city, String street) {
        Address address = createNewAddress();
        address.setCity(city);
        address.setStreet(street);
        address = addressRepository.save(address);
        return address;
    }

    /*Private & Protected Methods*/
    protected Address createNewAddress() {
        throw new IllegalStateException();
    }
}