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

    public enum DiscountState {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discount discount = (Discount) o;

        if (name != null ? !name.equals(discount.name) : discount.name != null) return false;
        return state == discount.state;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "name='" + name + '\'' +
                ", state=" + state +
                '}';
    }
}