package ua.fantotsy.services.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    public Pizza getPizzaById(Long id) {
        return pizzaRepository.findById(id);
    }

    @Override
    @Benchmark(value = false)
    @Transactional
    public Pizza addNewPizza(String name, Double price, Pizza.PizzaType type) {
        if (price <= 0.0) {
            throw new RuntimeException("Price is not available!");
        }
        Pizza newPizza = createNewPizza();
        newPizza.setName(name);
        newPizza.setPrice(price);
        newPizza.setType(type);
        newPizza = pizzaRepository.save(newPizza);
        return newPizza;
    }

    /*Private & Protected Methods*/
    protected Pizza createNewPizza() {
        throw new IllegalStateException();
    }
}