package ua.fantotsy.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.services.customer.CustomerService;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class CustomerRestController {
    private CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Customer> find(@PathVariable("customerId") Long customerId) {
        Customer customer = customerService.findById(customerId);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link link = linkTo(methodOn(CustomerRestController.class).find(customerId)).withSelfRel();
        customer.add(link);

        return new ResponseEntity<>(customer, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> create(@RequestBody Customer customer, UriComponentsBuilder builder) {
        System.out.println(customer);
        Customer newCustomer = customerService.addNewCustomer(customer.getName(), customer.getAddress().getCity(),
                customer.getAddress().getStreet(), customer.hasAccumulativeCard());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/find/{customerId}").buildAndExpand(newCustomer.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}