package ua.fantotsy.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.fantotsy.domain.discounts.AccumulativeCardDiscount;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.domain.discounts.DiscountManager;
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
    public void testConfirmCountsInitialPrice() {
        order.confirm();
        org.junit.Assert.assertEquals(1200.0, order.getInitialPrice(), eps);
    }

}