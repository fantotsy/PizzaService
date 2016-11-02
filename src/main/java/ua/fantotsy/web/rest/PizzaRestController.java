package ua.fantotsy.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PizzaRestController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello() {
        return "<b>Hello from rest controller</b>";
    }
}