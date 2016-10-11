package ua.fantotsy.domain.discounts;

import ua.fantotsy.domain.Order;

public interface Discount {

    void applyDiscount(Order order);

    boolean canBeApplied(Order order);
}