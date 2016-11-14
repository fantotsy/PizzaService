package ua.fantotsy.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.order.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class OrderRestController {
    private OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<Order> find(@PathVariable("orderId") Long orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Link link = linkTo(methodOn(OrderRestController.class).find(orderId)).withSelfRel();
        order.add(link);
        return new ResponseEntity<>(order, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> create(@RequestBody Order order, UriComponentsBuilder builder) {
        System.out.println(order);
        Order newOrder = orderService.addNewOrderByCustomerIdAndPizzaIds(order.getCustomer().getCustomerId(),
                pizzasIntoArray(order.getPizzas()));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/order/{orderId}").buildAndExpand(newOrder.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    private Long[] pizzasIntoArray(Map<Pizza, Integer> pizzas) {
        List<Long> listOfPizzas = pizzasMapIntoList(pizzas);
        Long[] result = new Long[listOfPizzas.size()];
        return listOfPizzas.toArray(result);
    }

    private List<Long> pizzasMapIntoList(Map<Pizza, Integer> pizzas) {
        List<Long> result = new ArrayList<>();
        for (Map.Entry<Pizza, Integer> entry : pizzas.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey().getPizzaId());
            }
        }
        return result;
    }
}