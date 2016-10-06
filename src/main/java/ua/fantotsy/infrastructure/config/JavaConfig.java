package ua.fantotsy.infrastructure.config;

import ua.fantotsy.repository.order.InMemoryOrderRepository;
import ua.fantotsy.repository.pizza.InMemoryPizzaRepository;
import ua.fantotsy.services.order.SimpleOrderService;
import ua.fantotsy.services.pizza.SimplePizzaService;

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