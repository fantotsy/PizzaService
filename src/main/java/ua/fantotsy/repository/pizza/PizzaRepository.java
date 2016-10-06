package ua.fantotsy.repository.pizza;

import ua.fantotsy.domain.Pizza;

import java.util.List;

public interface PizzaRepository {
    List<Pizza> getPizzas();

    Pizza getPizzaById(long id);
}