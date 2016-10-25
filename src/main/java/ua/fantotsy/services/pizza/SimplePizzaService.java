package ua.fantotsy.services.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.annotations.Benchmark;
import ua.fantotsy.repository.pizza.PizzaRepository;

public class SimplePizzaService implements PizzaService {
    /*Fields*/
    private PizzaRepository pizzaRepository;

    /*Constructors*/
    @Autowired
    public SimplePizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    /*Public Methods*/
    @Override
    public Pizza getPizzaById(long id) {
        return pizzaRepository.findById(id);
    }

    @Override
    @Benchmark(value = true)
    public void addNewPizza(String name, double price, Pizza.PizzaTypes type) {
        if (price <= 0.0) {
            throw new RuntimeException("Price is not available!");
        }
        Pizza newPizza = createNewPizza();
        newPizza.setName(name);
        newPizza.setPrice(price);
        newPizza.setType(type);
        pizzaRepository.save(newPizza);
    }

    /*Private & Protected Methods*/
    protected Pizza createNewPizza() {
        throw new IllegalStateException();
    }
}