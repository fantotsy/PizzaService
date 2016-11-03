package ua.fantotsy.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.pizza.PizzaService;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class PizzaRestController {
    private PizzaService pizzaService;

    @Autowired
    public PizzaRestController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @RequestMapping(value = "/pizza/{pizzaId}", method = RequestMethod.GET)
    public ResponseEntity<Pizza> find(@PathVariable("pizzaId") Long pizzaId) {
        Pizza pizza = pizzaService.findById(pizzaId);
        if (pizza == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link link = linkTo(methodOn(PizzaRestController.class).find(pizzaId)).withSelfRel();
        pizza.add(link);

        return new ResponseEntity<>(pizza, HttpStatus.FOUND);
    }

    @RequestMapping(
            value = "/pizza",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    public ResponseEntity<Void> create(@RequestBody Pizza pizza, UriComponentsBuilder builder) {
        System.out.println(pizza);
        Pizza p = pizzaService.addNewPizza(pizza.getName(), pizza.getPrice(), pizza.getType());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/find/{pizzaId}").buildAndExpand(p.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}