package ua.fantotsy.repository.pizza;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("pizzaRepository")
public class JpaPizzaRepository implements PizzaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Pizza findById(Long id) {
        return entityManager.find(Pizza.class, id);
    }

    @Override
    public Pizza save(Pizza pizza) {
        return entityManager.merge(pizza);
    }
}