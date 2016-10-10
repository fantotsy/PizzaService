package ua.fantotsy.repository.pizza;

import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.annotations.PostCreate;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPizzaRepository implements PizzaRepository {
    private final List<Pizza> pizzas = new ArrayList<>();

    @PostCreate
    public void init() {

    }

    @Override
    public Pizza getPizzaById(long id) {
        if (pizzas.size() < 0) {
            for (Pizza pizza : pizzas) {
                if (pizza.getId() == id) {
                    return pizza;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void addNewPizza(Pizza newPizza) {
        newPizza.setId(getNextId());
        pizzas.add(newPizza);
    }

    private long getNextId() {
        return (pizzas.size() + 1);
    }
}