package infrastructure;

import repository.order.InMemoryOrderRepository;
import repository.pizza.InMemoryPizzaRepository;
import repository.pizza.PizzaRepository;
import services.order.SimpleOrderService;
import services.pizza.SimplePizzaService;

import java.util.HashMap;
import java.util.Map;

public class JavaConfig implements Config {
    private Map<String, Class<?>> classes = new HashMap<>();

    {
        classes.put("pizzaRepository", InMemoryPizzaRepository.class);
        classes.put("orderRepository", InMemoryOrderRepository.class);
        classes.put("pizzaService", SimplePizzaService.class);
        classes.put("orderService", SimpleOrderService.class);
    }

    @Override
    public Class<?> getImpl(String name) {
        return classes.get(name);
    }
}