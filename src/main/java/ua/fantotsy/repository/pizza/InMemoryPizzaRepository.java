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
    public Pizza findById(Long id) {
        if (pizzas.size() > 0) {
            for (Pizza pizza : pizzas) {
                if (pizza.getId().equals(id)) {
                    return pizza;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Pizza findByName(String name) {
        if (pizzas.size() > 0) {
            for (Pizza pizza : pizzas) {
                if (pizza.getName().equals(name)) {
                    return pizza;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<Pizza> findAllPizzas() {
        return pizzas;
    }

    @Override
    public Pizza save(Pizza pizza) {
        pizza.setPizzaId(getNextId());
        pizzas.add(pizza);
        return pizza;
    }

    /*Private Methods*/
    private long getNextId() {
        return (pizzas.size() + 1);
    }
}