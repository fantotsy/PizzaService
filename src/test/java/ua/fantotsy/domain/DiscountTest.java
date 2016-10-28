package ua.fantotsy.domain;

import org.junit.Before;
import org.junit.Test;
import ua.fantotsy.domain.discounts.AccumulativeCardDiscount;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.domain.discounts.DiscountManager;
import ua.fantotsy.domain.discounts.TheMostExpensivePizzaDiscount;

import java.util.HashSet;
import java.util.Set;

public class DiscountTest {
    private final double eps = 0.00001;
    private Order order;
    private Customer customer;
    private AccumulativeCard accumulativeCard;
    private Pizza pizza1;
    private Pizza pizza2;
    private Pizza pizza3;

    @Before
    public void setUp() {
        Set<Discount> discounts = new HashSet<Discount>() {{
            add(new AccumulativeCardDiscount());
            add(new TheMostExpensivePizzaDiscount());
        }};
        DiscountManager.setActiveDiscounts(discounts);
        order = new Order();

        pizza1 = new Pizza("Name1", 100.0, Pizza.PizzaType.VEGETARIAN);
        pizza2 = new Pizza("Name2", 200.0, Pizza.PizzaType.SEA);
        pizza3 = new Pizza("Name3", 300.0, Pizza.PizzaType.MEAT);

        for (int i = 0; i < 2; i++) {
            order.addPizza(pizza1);
            order.addPizza(pizza2);
            order.addPizza(pizza3);
        }

        customer = new Customer();
        accumulativeCard = new AccumulativeCard();
        customer.setAccumulativeCard(accumulativeCard);
        order.setCustomer(customer);
    }

    @Test
    public void testDiscountMoreThanFourPizzas() {
        order.confirm();
        org.junit.Assert.assertEquals(180.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountLessThanFourPizzas() {
        order.removePizza(pizza1);
        order.removePizza(pizza2);
        order.removePizza(pizza3);
        order.confirm();
        org.junit.Assert.assertEquals(0.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountMoreThanFourPizzasWithCard() {
        customer.getAccumulativeCard().setBalance(100.0);
        order.confirm();
        org.junit.Assert.assertEquals(180.0, order.getDiscount(), eps);
        customer.getAccumulativeCard().setBalance(4000.0);
        order.setStatus(Order.OrderStatus.NEW);
        order.confirm();
        org.junit.Assert.assertEquals(360.0, order.getDiscount(), eps);
        customer.getAccumulativeCard().setBalance(3000.0);
        order.setStatus(Order.OrderStatus.NEW);
        order.confirm();
        org.junit.Assert.assertEquals(300.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountLessThanFourPizzasWithCard() {
        order.removePizza(pizza1);
        order.removePizza(pizza2);
        order.removePizza(pizza3);
        customer.getAccumulativeCard().setBalance(100.0);
        order.confirm();
        org.junit.Assert.assertEquals(10.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountMoreThanFourPizzasWithoutCard() {
        customer.setAccumulativeCard(null);
        order.confirm();
        org.junit.Assert.assertEquals(180.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountLessThanFourPizzasWithoutCard() {
        order.removePizza(pizza1);
        order.removePizza(pizza2);
        order.removePizza(pizza3);
        customer.setAccumulativeCard(null);
        order.confirm();
        org.junit.Assert.assertEquals(0.0, order.getDiscount(), eps);
    }
}