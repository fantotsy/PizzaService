package repository;

import domain.Pizza;

public interface PizzaRepository {
    Pizza getPizzaById(long id);
}