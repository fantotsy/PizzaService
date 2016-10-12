package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;

public interface PizzaService {

    Pizza getPizzaById(long id);

    void addNewPizza(Pizza newPizza);
}