package ua.fantotsy.repository.pizza;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("pizzaRepository")
public class JpaPizzaRepository implements PizzaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Pizza findById(Long id) {
        return entityManager.find(Pizza.class, id);
    }

    @Override
    public Pizza findByName(String name) {
        return entityManager.createNamedQuery("Pizza.findByName", Pizza.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Pizza> findAllPizzas() {
        return entityManager.createNamedQuery("Pizza.findAllPizzas", Pizza.class).getResultList();
    }

    @Override
    public Pizza save(Pizza pizza) {
        return entityManager.merge(pizza);
    }
}