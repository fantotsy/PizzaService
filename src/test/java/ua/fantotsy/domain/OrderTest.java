package ua.fantotsy.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.fantotsy.domain.discounts.AccumulativeCardDiscount;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.domain.discounts.TheMostExpensivePizzaDiscount;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {
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
        order = new Order(discounts);

        pizza1 = new Pizza();
        pizza1.setId(1L);
        pizza1.setPrice(100.0);

        pizza2 = new Pizza();
        pizza2.setId(2L);
        pizza2.setPrice(200.0);

        pizza3 = new Pizza();
        pizza3.setId(3L);
        pizza3.setPrice(300.0);

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
    public void testAddPizza() {
        order.addPizza(pizza1);
        org.junit.Assert.assertEquals(7, order.getAmountOfPizzas());
    }

    @Test
    public void testRemovePizza() {
        order.removePizza(pizza1);
        org.junit.Assert.assertEquals(5, order.getAmountOfPizzas());
    }

    @Test
    public void testCountTotalPriceCountsInitialPrice() {
        order.countTotalPrice();
        org.junit.Assert.assertEquals(1200.0, order.getInitialPrice(), eps);
    }

    @Test
    public void testDiscountMoreThanFourPizzas() {
        order.countTotalPrice();
        org.junit.Assert.assertEquals(180.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountLessThanFourPizzas() {
        order.removePizza(pizza1);
        order.removePizza(pizza2);
        order.removePizza(pizza3);
        order.countTotalPrice();
        org.junit.Assert.assertEquals(0.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountMoreThanFourPizzasWithCard() {
        customer.getAccumulativeCard().setBalance(100.0);
        order.countTotalPrice();
        org.junit.Assert.assertEquals(180.0, order.getDiscount(), eps);
        customer.getAccumulativeCard().setBalance(4000.0);
        order.countTotalPrice();
        org.junit.Assert.assertEquals(360.0, order.getDiscount(), eps);
        customer.getAccumulativeCard().setBalance(3000.0);
        order.countTotalPrice();
        org.junit.Assert.assertEquals(300.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountLessThanFourPizzasWithCard() {
        order.removePizza(pizza1);
        order.removePizza(pizza2);
        order.removePizza(pizza3);
        customer.getAccumulativeCard().setBalance(100.0);
        order.countTotalPrice();
        org.junit.Assert.assertEquals(10.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountMoreThanFourPizzasWithoutCard() {
        customer.setAccumulativeCard(null);
        order.countTotalPrice();
        org.junit.Assert.assertEquals(180.0, order.getDiscount(), eps);
    }

    @Test
    public void testDiscountLessThanFourPizzasWithoutCard() {
        order.removePizza(pizza1);
        order.removePizza(pizza2);
        order.removePizza(pizza3);
        customer.setAccumulativeCard(null);
        order.countTotalPrice();
        org.junit.Assert.assertEquals(0.0, order.getDiscount(), eps);
    }
}