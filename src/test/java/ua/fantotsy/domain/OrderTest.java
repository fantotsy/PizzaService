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
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {
    private final double eps = 0.00001;
    Order order;
    Pizza pizza1;
    Pizza pizza2;
    Pizza pizza3;

    @Before
    public void setUp() {
        Set<Discount> discounts = new HashSet<Discount>() {{
            add(new AccumulativeCardDiscount());
            add(new TheMostExpensivePizzaDiscount());
        }};
        order = new Order(discounts);

        order.setPizzas(new ArrayList<>());

        pizza1 = new Pizza();
        pizza1.setPrice(100.0);

        pizza2 = new Pizza();
        pizza2.setPrice(200.0);

        pizza3 = new Pizza();
        pizza3.setPrice(300.0);

        for (int i = 0; i < 2; i++) {
            order.addPizza(pizza1);
            order.addPizza(pizza2);
            order.addPizza(pizza3);
        }

        order.setCustomer(new Customer());
    }

    @Test
    public void testCountTotalPriceCountsInitialPrice() {
        order.countTotalPrice();
        org.junit.Assert.assertEquals(1200.0, order.getInitialPrice(), eps);
    }

    @Test
    public void testCountTotalPriceCountsDiscountWithCard() {
        order.countTotalPrice();
        org.junit.Assert.assertEquals(180.0, order.getDiscount(), eps);
    }
}