package ua.fantotsy.domain.discounts;

import ua.fantotsy.domain.Order;

public abstract class Discount {
    protected String name;
    protected DiscountState state;

    public enum DiscountState {
        ACTIVE, INACTIVE;
    }

    public Discount() {
        name = this.getClass().getSimpleName();
        state = DiscountState.ACTIVE;
    }

    public abstract boolean canBeApplied(Order order);

    public abstract double getDiscount(Order order);

    public String getName() {
        return name;
    }

    public DiscountState getState(){
        return state;
    }

}