package ua.fantotsy.domain.discounts;

import org.springframework.stereotype.Component;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component
public class TheMostExpensivePizzaDiscount extends Discount {
    private final static int MIN_AMOUNT_OF_PIZZAS = 4;
    private final static int PIZZA_PRICE_PERCENTAGE = 30;
    private Order order;

    @Override
    public boolean canBeApplied(Order order) {
        this.order = order;
        return isEnoughPizzasInOrder();
    }

    @Override
    public double getDiscount(Order order) {
        List<Pizza> theMostExpensivePizzas = getTheMostExpensivePizzas();
        double discount = 0;
        for (Pizza pizza : theMostExpensivePizzas) {
            discount += Utils.getPercentageOfNumber(pizza.getPrice(), PIZZA_PRICE_PERCENTAGE);
        }
        return discount;
    }

    private boolean isEnoughPizzasInOrder() {
        return (order.getAmountOfPizzas() > MIN_AMOUNT_OF_PIZZAS);
    }

    private List<Pizza> getTheMostExpensivePizzas() {
        if (isEmpty()) {
            throw new RuntimeException("Order is empty.");
        } else {
            List<Pizza> result = new ArrayList<>();
            double maxPrice = getPizzaInOrderById(0).getPrice();
            for (Pizza pizza : getPizzasFromOrder()) {
                if (pizza.getPrice() < maxPrice) {
                    maxPrice = pizza.getPrice();
                }
            }
            for (Pizza pizza : getPizzasFromOrder()) {
                if (pizza.getPrice() == maxPrice) {
                    result.add(pizza);
                }
            }
            return result;
        }
    }

    private boolean isEmpty() {
        return order.isEmpty();
    }

    private Pizza getPizzaInOrderById(int id) {
        return order.getOrder().get(id);
    }

    private List<Pizza> getPizzasFromOrder() {
        return order.getOrder();
    }
}