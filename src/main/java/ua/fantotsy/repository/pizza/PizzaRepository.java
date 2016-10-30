package ua.fantotsy.repository.pizza;

import ua.fantotsy.domain.Pizza;

public interface PizzaRepository {

    Pizza findById(Long id);

    Pizza findByName(String name);

    Pizza save(Pizza pizza);
}