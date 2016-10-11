package ua.fantotsy.services.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.pizza.PizzaRepository;

@Service
public class SimplePizzaService implements PizzaService {
    /*Fields*/
    private PizzaRepository pizzaRepository;

    /*Constructors*/
    @Autowired
    public SimplePizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    /*Methods*/
    @Override
    public Pizza getPizzaById(long id) {
        return pizzaRepository.getPizzaById(id);
    }

    @Override
    public void addNewPizza(Pizza newPizza) {
        pizzaRepository.addNewPizza(newPizza);
    }
}