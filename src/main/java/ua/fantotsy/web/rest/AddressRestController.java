package ua.fantotsy.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.fantotsy.domain.Address;
import ua.fantotsy.services.address.AddressService;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class AddressRestController {
    private AddressService addressService;

    @Autowired
    public AddressRestController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    public ResponseEntity<Address> find(@PathVariable("addressId") Long addressId) {
        Address address = addressService.findById(addressId);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Link link = linkTo(methodOn(AddressRestController.class).find(addressId)).withSelfRel();
        address.add(link);
        return new ResponseEntity<>(address, HttpStatus.FOUND);
    }
}