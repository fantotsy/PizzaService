package ua.fantotsy;

import ua.fantotsy.domain.*;
import ua.fantotsy.domain.discounts.AccumulativeCardDiscount;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.domain.discounts.TheMostExpensivePizzaDiscount;
import ua.fantotsy.services.order.OrderService;
import ua.fantotsy.services.order.SimpleOrderService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JpaAppRunner {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Pizza pizza1 = new Pizza("Diabola", 200.0, Pizza.PizzaTypes.MEAT);
        Pizza pizza2 = new Pizza("Neapolitana", 300.0, Pizza.PizzaTypes.VEGETARIAN);
        Pizza pizza3 = new Pizza("Hawaii", 400.0, Pizza.PizzaTypes.SEA);

        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(pizza1, 2);
            put(pizza2, 3);
            put(pizza3, 4);
        }};

        Address address = new Address("Kyiv", "K18a");

        Customer customer1 = new Customer("Vasya", address, new AccumulativeCard());
        Customer customer2 = new Customer("Petya", address, new AccumulativeCard());
        Customer customer3 = new Customer("Fedya", address, new AccumulativeCard());

        Set<Discount> discounts = new HashSet<Discount>(){{
            add(new TheMostExpensivePizzaDiscount());
            add(new AccumulativeCardDiscount());
        }};

        Order order1 = new Order(pizzas, customer1, discounts);
        Order order2 = new Order(pizzas, customer2, discounts);
        Order order3 = new Order(pizzas, customer3, discounts);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(pizza1);
        entityManager.persist(pizza2);
        entityManager.persist(pizza3);

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(order3);

        entityTransaction.commit();
        entityManager.clear();//To search in database even if there is such object in context.

        //Pizza foundPizza = entityManager.find(Pizza.class, 4L);
        //System.out.println(pizza == foundPizza);

        entityManager.close();
        entityManagerFactory.close();
    }
}