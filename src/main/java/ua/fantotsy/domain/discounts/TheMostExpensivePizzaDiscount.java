package ua.fantotsy.domain.discounts;

import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

import java.util.ArrayList;
import java.util.List;

public class TheMostExpensivePizzaDiscount implements Discount {
    private final int minAmountOfPizzas = 4;
    private final int percentage = 30;
    private Order order;

    @Override
    public boolean canBeApplied(Order order) {
        this.order = order;
        return isEnoughPizzasInOrder();
    }

    @Override
    public void applyDiscount(Order order){
        List<Pizza> theMostExpensivePizzas = getTheMostExpensivePizzas();
    }

    private boolean isEnoughPizzasInOrder() {
        return (order.getAmountOfPizzas() > minAmountOfPizzas);
    }

    private List<Pizza> getTheMostExpensivePizzas() {
        if (isEmpty()) {
            throw new RuntimeException("Order is empty.");
        } else {
            List<Pizza> result = new ArrayList<>();
            double maxPrice = order.getOrder().get(0).getPrice();
            for (Pizza pizza : order.getOrder()) {
                if (pizza.getPrice() < maxPrice) {
                    maxPrice = pizza.getPrice();
                }
            }
            for (Pizza pizza : order.getOrder()) {
                if (pizza.getPrice() == maxPrice) {
                    result.add(pizza);
                }
            }
            return result;
        }
    }

    private boolean isEmpty() {
        return (order.getAmountOfPizzas() == 0);
    }



}