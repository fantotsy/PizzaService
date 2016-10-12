package ua.fantotsy.repository.address;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Address;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryAddressRepository implements AddressRepository {
    /*Fields*/
    private final List<Address> addresses;

    /*Constructors*/
    public InMemoryAddressRepository() {
        addresses = new ArrayList<>();
    }

    /*Public Methods*/
    @Override
    public Address getAddressById(long id) {
        if (addresses.size() > 0) {
            for (Address address : addresses) {
                if (address.getId() == id) {
                    return address;
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public void addNewAddress(Address address) {
        addresses.add(address);
    }
}