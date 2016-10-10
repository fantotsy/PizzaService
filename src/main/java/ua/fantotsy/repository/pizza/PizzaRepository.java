package ua.fantotsy.repository.pizza;

import ua.fantotsy.domain.Pizza;

import java.util.List;

public interface PizzaRepository {

    Pizza getPizzaById(long id);

    void addNewPizza(Pizza newPizza);
}