package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.pizza.PizzaRepository;

public interface PizzaService {

    Pizza getPizzaById(Integer id);

    void addNewPizza(Pizza newPizza);
}