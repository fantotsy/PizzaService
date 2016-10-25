package ua.fantotsy.repository.pizza;

import ua.fantotsy.domain.Pizza;

public interface PizzaRepository {

    Pizza findById(Long id);

    Pizza save(Pizza pizza);
}