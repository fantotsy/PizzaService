package ua.fantotsy.services.pizza;

import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.pizza.PizzaRepository;

public class SimplePizzaService implements PizzaService {
    private PizzaRepository pizzaRepository;

    public SimplePizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public PizzaRepository getPizzaRepository() {
        return pizzaRepository;
    }

    @Override
    public Pizza getPizzaById(Integer id) {
        return pizzaRepository.getPizzaById(id);
    }
}