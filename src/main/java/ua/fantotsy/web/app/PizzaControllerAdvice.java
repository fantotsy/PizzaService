package ua.fantotsy.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.pizza.PizzaService;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class PizzaControllerAdvice {

    private final PizzaService pizzaService;

    @Autowired
    public PizzaControllerAdvice(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @ModelAttribute
    public Pizza status(@RequestParam(name = "pizzaId", required = false) Pizza pizza) {
        return pizza;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleError(Exception e, HttpServletRequest request, Model model) {
        model.addAttribute("ex", e);
        model.addAttribute("url", request.getRequestURL());
        return "error";
    }
}