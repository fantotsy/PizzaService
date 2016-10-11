package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.annotations.BenchMark;
import ua.fantotsy.repository.pizza.PizzaRepository;

public interface PizzaService {

    Pizza getPizzaById(long id);

    void addNewPizza(Pizza newPizza);
}