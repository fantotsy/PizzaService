package ua.fantotsy.web.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PizzaController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}