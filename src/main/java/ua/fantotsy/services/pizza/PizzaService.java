package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;

public interface PizzaService {

    Pizza getPizzaById(long id);

    void addNewPizza(String name, double price, Pizza.PizzaTypes type);
}