package repository.pizza;

import domain.Pizza;

import java.util.List;

public interface PizzaRepository {
    List<Pizza> getPizzas();

    Pizza getPizzaById(long id);
}