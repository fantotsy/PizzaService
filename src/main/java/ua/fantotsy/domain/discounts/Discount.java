package ua.fantotsy.domain.discounts;

import ua.fantotsy.domain.Order;

import javax.persistence.*;

@Entity
@Table(name = "discounts")
public abstract class Discount {
    /*Fields*/
    @Id
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state", nullable = false)
    private DiscountState state;
    @Transient
    protected Order order;

    /*Constructors*/
    public Discount() {
        name = this.getClass().getSimpleName();
        state = DiscountState.ACTIVE;
    }

    public Discount(String name, DiscountState state) {
        this.name = name;
        this.state = state;
    }

    /*Internal Objects*/
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

    /*Abstract Methods*/
    public abstract boolean canBeApplied(Order order);

    public abstract double getDiscount(Order order);

    /*Public Methods*/
    public void changeState() {
        state = state.reverseState();
    }

    /*Getters & Setters*/
    public String getName() {
        return name;
    }

    public DiscountState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount)) return false;

        Discount discount = (Discount) o;

        return name != null ? name.equals(discount.name) : discount.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Discount name: " + name +
                "\nDiscount state: " + state.name() + "\n";
    }
}