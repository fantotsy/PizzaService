package services.pizza;

import domain.Pizza;
import infrastructure.InitialContext;
import repository.pizza.InMemoryPizzaRepository;
import repository.pizza.PizzaRepository;

public class SimplePizzaService implements PizzaService {
    private PizzaRepository pizzaRepository;

    public SimplePizzaService() {
        pizzaRepository = new InMemoryPizzaRepository();
        InitialContext context = new InitialContext();
        pizzaRepository = context.getInstance("pizzaRepository");
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