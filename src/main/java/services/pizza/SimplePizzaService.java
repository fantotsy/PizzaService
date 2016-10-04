package services.pizza;

import domain.Pizza;
import repository.pizza.InMemoryPizzaRepository;
import repository.pizza.PizzaRepository;

import java.util.List;

public class SimplePizzaService implements PizzaService {
    private PizzaRepository pizzaRepository = new InMemoryPizzaRepository();

    public PizzaRepository getPizzaRepository() {
        return pizzaRepository;
    }

    @Override
    public Pizza getPizzaById(Integer id) {
        return pizzaRepository.getPizzaById(id);
    }
}