package ua.fantotsy.domain.discounts;

import ua.fantotsy.domain.Order;

public abstract class Discount {
    private String name;
    private DiscountState state;
    protected Order order;

    Discount() {
        name = this.getClass().getSimpleName();
        state = DiscountState.ACTIVE;
    }

    private enum DiscountState {
        ACTIVE {
            @Override
            public DiscountState reverseState() {
                return INACTIVE;
            }
        },
        INACTIVE {
            @Override
            public DiscountState reverseState() {
                return ACTIVE;
            }
        };

        public abstract DiscountState reverseState();
    }

    public abstract boolean canBeApplied(Order order);

    public abstract double getDiscount(Order order);

    public void changeState() {
        state = state.reverseState();
    }

    public String getName() {
        return name;
    }

    public DiscountState getState() {
        return state;
    }

}