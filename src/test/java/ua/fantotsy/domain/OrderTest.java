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

    @Before
    public void setUp() {
        Set<Discount> discounts = new HashSet<Discount>() {{
            add(new AccumulativeCardDiscount());
            add(new TheMostExpensivePizzaDiscount());
        }};
        order = new Order(discounts);

        Pizza pizza = new Pizza();
        pizza.setPrice(100.0);
        List<Pizza> pizzas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pizzas.add(pizza);
        }
        order.setPizzas(pizzas);
    }

    @Test
    public void testCountInitialPrice() {

    }
}