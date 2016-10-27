package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;

public interface PizzaService {

    Pizza findPizzaById(Long id);

    Pizza addNewPizza(String name, Double price, Pizza.PizzaType type);
}