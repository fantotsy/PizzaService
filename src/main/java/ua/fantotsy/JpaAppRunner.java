package ua.fantotsy;

import ua.fantotsy.domain.*;
import ua.fantotsy.domain.discounts.AccumulativeCardDiscount;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.domain.discounts.TheMostExpensivePizzaDiscount;

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

        Pizza pizza1 = new Pizza("Diabola", 200.0, Pizza.PizzaType.MEAT);
        Pizza pizza2 = new Pizza("Neapolitana", 300.0, Pizza.PizzaType.VEGETARIAN);
        Pizza pizza3 = new Pizza("Hawaii", 400.0, Pizza.PizzaType.SEA);

        Map<Pizza, Integer> pizzas1 = new HashMap<Pizza, Integer>() {{
            put(pizza1, 2);
            put(pizza2, 3);
            put(pizza3, 4);
        }};
        Map<Pizza, Integer> pizzas2 = new HashMap<Pizza, Integer>() {{
            put(pizza1, 2);
            put(pizza2, 3);
            put(pizza3, 4);
        }};
        Map<Pizza, Integer> pizzas3 = new HashMap<Pizza, Integer>() {{
            put(pizza1, 2);
            put(pizza2, 3);
            put(pizza3, 4);
        }};

        Address address = new Address("Kyiv", "K18a");

        Customer customer1 = new Customer("Vasya", address, new AccumulativeCard(12345L));
        Customer customer2 = new Customer("Petya", address, new AccumulativeCard(23456L));
        Customer customer3 = new Customer("Fedya", address, new AccumulativeCard(12121L));

        Set<Discount> discounts = new HashSet<Discount>() {{
            add(new TheMostExpensivePizzaDiscount());
            add(new AccumulativeCardDiscount());
        }};

        Order order1 = new Order(pizzas1, customer1, discounts);
        Order order2 = new Order(pizzas2, customer2, discounts);
        Order order3 = new Order(pizzas3, customer3, discounts);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        persistPizzas(entityManager, pizza1, pizza2, pizza3);

        persistOrders(entityManager, order1, order2, order3);

        confirmOrder(order1);
        payOrder(order1);

        entityTransaction.commit();

        entityTransaction.begin();

        Order order = entityManager.find(Order.class, 4L);
        Pizza p = entityManager.find(Pizza.class, 1L);
        removePizza(order, pizza1);

        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void persistPizzas(EntityManager entityManager, Pizza... pizzas) {
        for (Pizza pizza : pizzas) {
            entityManager.persist(pizza);
        }
    }

    private static void persistOrders(EntityManager entityManager, Order... orders) {
        for (Order order : orders) {
            entityManager.persist(order);
        }
    }

    private static void confirmOrder(Order order) {
        order.confirm();
    }

    private static void payOrder(Order order) {
        order.pay();
    }

    private static void removePizza(Order order, Pizza pizza){
        order.removePizza(pizza);
    }
}