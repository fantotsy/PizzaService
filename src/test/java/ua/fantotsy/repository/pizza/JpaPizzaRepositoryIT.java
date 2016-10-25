package ua.fantotsy.repository.pizza;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.RepositoryTestConfig;

import static org.junit.Assert.assertNotNull;

public class JpaPizzaRepositoryIT extends RepositoryTestConfig {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Test
    public void testFind() {

    }

    @Test
    public void testSavePizza() {
        Pizza pizza = new Pizza("Sea", 200.0, Pizza.PizzaTypes.SEA);
        pizza = pizzaRepository.save(pizza);
        assertNotNull(pizza.getId());
    }
}