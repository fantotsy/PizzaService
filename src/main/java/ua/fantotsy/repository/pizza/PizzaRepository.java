package ua.fantotsy.repository.pizza;

import ua.fantotsy.domain.Pizza;

import java.util.List;

public interface PizzaRepository {

    Pizza findById(Long id);

    Pizza findByName(String name);

    List<Pizza> findAllPizzas();

    Pizza save(Pizza pizza);
}