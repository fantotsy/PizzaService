package services.pizza;

import domain.Pizza;
import repository.pizza.PizzaRepository;

public interface PizzaService {
    PizzaRepository getPizzaRepository();

    Pizza getPizzaById(Integer id);
}