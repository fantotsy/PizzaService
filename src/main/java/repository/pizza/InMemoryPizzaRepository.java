package repository.pizza;

import domain.Pizza;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPizzaRepository implements PizzaRepository {
    private final List<Pizza> pizzas;

    public InMemoryPizzaRepository() {
        pizzas = new ArrayList<Pizza>() {{
            add(new Pizza(1L, "First", 100.0, Pizza.PizzaTypes.Vegetarian));
            add(new Pizza(2L, "Second", 200.0, Pizza.PizzaTypes.Sea));
            add(new Pizza(3L, "Third", 300.0, Pizza.PizzaTypes.Meat));
            add(new Pizza(4L, "Third", 400.0, Pizza.PizzaTypes.Meat));
        }};
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public Pizza getPizzaById(long id) {
        for (Pizza pizza : pizzas) {
            if (pizza.getId() == id) {
                return pizza;
            }
        }
        throw new IllegalArgumentException();
    }
}