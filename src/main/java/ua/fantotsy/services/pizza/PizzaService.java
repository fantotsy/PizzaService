package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.pizza.PizzaRepository;

public interface PizzaService {
    PizzaRepository getPizzaRepository();

    Pizza getPizzaById(Integer id);
}