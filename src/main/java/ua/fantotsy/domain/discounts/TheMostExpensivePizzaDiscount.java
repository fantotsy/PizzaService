package ua.fantotsy.domain.discounts;

import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;

import java.util.ArrayList;
import java.util.List;

public class TheMostExpensivePizzaDiscount implements Discount {
    private final int percentage = 30;
    private Order order;

    @Override
    public boolean canBeApplied(Order order) {
        this.order = order;
        return true;
        //return isMoreThanFourPizzasInOrder();
    }

    @Override
    public void applyDiscount(Order order){

    }






}