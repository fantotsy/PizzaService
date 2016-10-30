package ua.fantotsy.repository.pizza;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.RepositoryTestConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JpaPizzaRepositoryIT extends RepositoryTestConfig {
    @Autowired
    private PizzaRepository pizzaRepository;

    @After
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM pizzas");
    }

    @Test
    public void testFindPizzaById() {
        Pizza pizza = new Pizza("Name1", 100.0, Pizza.PizzaType.SEA);
        jdbcTemplate.update("INSERT INTO pizzas (id, name, price, status, type) VALUES (1, ?, ?, ?, ?)",
                pizza.getName(), pizza.getPrice(), pizza.getStatus().name(), pizza.getType().name());
        pizza = pizzaRepository.findById(1L);
        long id = pizza.getId();
        assertEquals(1L, id);
    }

    @Test
    public void testSetIdAfterSave() {
        Pizza pizza = new Pizza("Sea", 200.0, Pizza.PizzaType.SEA);
        pizza = pizzaRepository.save(pizza);
        assertNotNull(pizza.getId());
    }
}