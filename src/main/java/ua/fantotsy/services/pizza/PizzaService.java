package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;

import java.util.List;

public interface PizzaService {

    Pizza findById(Long id);

    Pizza findByName(String name);

    List<Pizza> findAllPizzas();

    Pizza addNewPizza(String name, Double price, Pizza.PizzaType type);
}