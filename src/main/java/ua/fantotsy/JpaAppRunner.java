package ua.fantotsy;

import ua.fantotsy.domain.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaAppRunner {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Pizza pizza = new Pizza(4L, "Diabola", 200.0, Pizza.PizzaTypes.MEAT);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        //entityManager.persist(pizza);
        entityTransaction.commit();
        entityManager.clear();//To search in database even if there is such object in context.

        //Pizza foundPizza = entityManager.find(Pizza.class, 4L);
        //System.out.println(pizza == foundPizza);

        entityManager.close();
        entityManagerFactory.close();
    }
}