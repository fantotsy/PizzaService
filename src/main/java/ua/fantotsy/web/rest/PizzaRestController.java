package ua.fantotsy.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.pizza.PizzaService;

@RestController
public class PizzaRestController {
    @Autowired
    private PizzaService pizzaService;

    @RequestMapping(value = "/pizza/{pizzaId}", method = RequestMethod.GET)
    public Pizza hello(@PathVariable("pizzaId") Long pizzaId) {
        return pizzaService.findById(3L);
    }

    @RequestMapping(
            value = "/pizza",
            method = RequestMethod.POST
    )
    public void pizza(@RequestBody Pizza pizza) {
        System.out.println(pizza);
    }
}