package ua.fantotsy.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.fantotsy.domain.AccumulativeCard;
import ua.fantotsy.services.accumulativeCard.AccumulativeCardService;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class AccumulativeCardRestController {
    private AccumulativeCardService accumulativeCardService;

    @Autowired
    public AccumulativeCardRestController(AccumulativeCardService accumulativeCardService) {
        this.accumulativeCardService = accumulativeCardService;
    }

    @RequestMapping(value = "/accumulative_card/{accumulativeCardId}", method = RequestMethod.GET)
    public ResponseEntity<AccumulativeCard> find(@PathVariable("accumulativeCardId") Long accumulativeCardId) {
        AccumulativeCard accumulativeCard = accumulativeCardService.findById(accumulativeCardId);
        if (accumulativeCard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link link = linkTo(methodOn(AccumulativeCardRestController.class).find(accumulativeCardId)).withSelfRel();
        accumulativeCard.add(link);

        return new ResponseEntity<>(accumulativeCard, HttpStatus.FOUND);
    }
}