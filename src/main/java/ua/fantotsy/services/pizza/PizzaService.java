package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;

public interface PizzaService {

    Pizza findById(Long id);

    Pizza findByName(String name);

    Pizza addNewPizza(String name, Double price, Pizza.PizzaType type);
}