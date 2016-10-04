package infrastructure;

import repository.pizza.InMemoryPizzaRepository;
import repository.pizza.PizzaRepository;

import java.util.HashMap;
import java.util.Map;

public class JavaConfig implements Config {
    private Map<String, Class<?>> classes = new HashMap<>();

    {
        classes.put("pizzaRepository", InMemoryPizzaRepository.class);
    }

    @Override
    public Class<?> getImpl(String name) {
        return classes.get(name);
    }
}