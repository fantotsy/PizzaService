package ua.fantotsy.services.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void init() {
        addNewPizza("Diabola", 300.0, Pizza.PizzaTypes.MEAT);
    }

    /*Public Methods*/
    @Override
    public Pizza getPizzaById(long id) {
        return pizzaRepository.getPizzaById(id);
    }

    @Override
    @Benchmark(value = true)
    public void addNewPizza(String name, double price, Pizza.PizzaTypes type) {
        Pizza newPizza = createNewPizza();
        newPizza.setName(name);
        newPizza.setPrice(price);
        newPizza.setType(type);
        pizzaRepository.addNewPizza(newPizza);
    }

    /*Private & Protected Methods*/
    protected Pizza createNewPizza() {
        throw new IllegalStateException();
    }
}