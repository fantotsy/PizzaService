package ua.fantotsy.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.ArrayList;

@Controller
public class PizzaController {
    @Autowired
    private PizzaService pizzaService;

    @RequestMapping("/pizzas")
    public ModelAndView pizzas(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pizzas");
        mv.setStatus(HttpStatus.OK);
        mv.addObject("pizzas", pizzaService.findAllPizzas());
        return mv;
    }

    @RequestMapping(value = "/{pizzaId}", method = RequestMethod.GET)
    public ModelAndView editPizza(@PathVariable("pizzaId") Long pizzaId){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("edit");
        mv.setStatus(HttpStatus.OK);
        mv.addObject("pizza", pizzaService.findById(pizzaId));
        return mv;
    }

    @RequestMapping(name = "/add-new", method = RequestMethod.POST)
    public String editPizza(@ModelAttribute Pizza pizza){
        pizzaService.addNewPizza(pizza.getName(), pizza.getPrice(), pizza.getType());
        return "redirect:pizzas";
    }


}