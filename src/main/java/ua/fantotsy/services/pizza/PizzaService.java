package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;

public interface PizzaService {

    Pizza getPizzaById(Long id);

    Pizza addNewPizza(String name, Double price, Pizza.PizzaType type);
}