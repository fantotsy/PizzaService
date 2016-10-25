package ua.fantotsy.repository.pizza;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Pizza;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryPizzaRepository implements PizzaRepository {
    /*Fields*/
    private final List<Pizza> pizzas;

    /*Constructors*/
    public InMemoryPizzaRepository() {
        pizzas = new ArrayList<>();
    }

    /*Public Methods*/
    @PostConstruct
    public void init() {

    }

    @Override
    public Pizza getPizzaById(long id) {
        if (pizzas.size() > 0) {
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

    @Override
    public Pizza save(Pizza pizza) {
        return null;
    }

    /*Private Methods*/
    private long getNextId() {
        return (pizzas.size() + 1);
    }
}