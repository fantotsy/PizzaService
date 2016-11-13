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
    public Address findById(Long id) {
        if (addresses.size() > 0) {
            for (Address address : addresses) {
                if (address.getId().equals(id)) {
                    return address;
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public Address save(Address address) {
        address.setAddressId(getNextId());
        addresses.add(address);
        return address;
    }

    /*Private Methods*/
    private long getNextId() {
        return (addresses.size() + 1);
    }
}