package ua.fantotsy.web.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.pizza.PizzaService;
import ua.fantotsy.web.infrastructure.MyController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/hello")
public class HelloController implements MyController {
    @Autowired
    private PizzaService pizzaService;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("HelloController");
            pizzaService.addNewPizza("N", 100.0, Pizza.PizzaType.SEA);
            out.println(pizzaService.findByName("N"));
        }
    }
}