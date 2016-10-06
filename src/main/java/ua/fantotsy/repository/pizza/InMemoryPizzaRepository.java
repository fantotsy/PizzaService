package ua.fantotsy.repository.pizza;

import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.annotations.PostCreate;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPizzaRepository implements PizzaRepository {
    private final List<Pizza> pizzas = new ArrayList<>();

    @PostCreate
    public void init() {
        pizzas.add(new Pizza(1L, "First", 100.0, Pizza.PizzaTypes.Vegetarian));
        pizzas.add(new Pizza(2L, "Second", 200.0, Pizza.PizzaTypes.Sea));
        pizzas.add(new Pizza(3L, "Third", 300.0, Pizza.PizzaTypes.Meat));
        pizzas.add(new Pizza(4L, "Third", 400.0, Pizza.PizzaTypes.Meat));
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