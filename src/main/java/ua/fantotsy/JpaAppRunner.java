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

        Customer customer1 = new Customer("Vasya", address, new AccumulativeCard("123456788765432"));
        Customer customer2 = new Customer("Petya", address, new AccumulativeCard("1234567887654321"));
        Customer customer3 = new Customer("Fedya", address, new AccumulativeCard("1212121212121212"));

        Set<Discount> discounts = new HashSet<Discount>() {{
            add(new TheMostExpensivePizzaDiscount());
            add(new AccumulativeCardDiscount());
        }};

        Order order1 = new Order(pizzas, customer1, discounts);
        Order order2 = new Order(pizzas, customer2, discounts);
        Order order3 = new Order(pizzas, customer3, discounts);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        persistPizzas(entityManager, pizza1, pizza2, pizza3);

        persistOrders(entityManager, order1, order2, order3);

        removePizza(order1, pizza1);

        confirmOrder(order1);
        payOrder(order1);

        entityTransaction.commit();

        Pizza pizza4 = new Pizza("Diabola", 200.0, Pizza.PizzaTypes.MEAT);
        pizza4.setId(1L);

        Pizza pizza5 = new Pizza("fgbb", 200.0, Pizza.PizzaTypes.MEAT);
        pizza5.setId(1L);


        Pizza p = entityManager.find(Pizza.class, 1L);
        System.out.println("1vs4: " + p.equals(pizza4));
        System.out.println("1vs5: " + p.equals(pizza5));
        entityTransaction.begin();

        confirmOrder(order2);
        payOrder(order2);

        entityTransaction.commit();

        Pizza foundPizza = entityManager.find(Pizza.class, 1L);
        System.out.println(pizza1);
        System.out.println(foundPizza);
        System.out.println(pizza1.equals(foundPizza));

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