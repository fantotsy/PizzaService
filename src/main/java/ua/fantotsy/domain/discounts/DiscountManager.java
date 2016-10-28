package ua.fantotsy.domain.discounts;

import org.springframework.beans.factory.annotation.Autowired;
import ua.fantotsy.domain.Order;

import java.util.HashSet;
import java.util.Set;

public class DiscountManager {
    private static Set<Discount> activeDiscounts;

    @Autowired
    public DiscountManager(Set<Discount> discounts) {
        activeDiscounts = extractActiveDiscounts(discounts);
    }

    public static Order countDiscount(Order order) {
        Double maxDiscount = 0.0;
        for (Discount discount : activeDiscounts) {
            if (discount.canBeApplied(order)) {
                Double currentDiscount = discount.getDiscount(order);
                if (currentDiscount > maxDiscount) {
                    maxDiscount = currentDiscount;
                    order.setAppliedDiscount(discount);
                }
            }
        }
        order.setDiscount(maxDiscount);
        return order;
    }

    private Set<Discount> extractActiveDiscounts(Set<Discount> discounts) {
        Set<Discount> result = new HashSet<>();
        for (Discount discount : discounts) {
            if (discount.getState().equals(Discount.DiscountState.ACTIVE)) {
                result.add(discount);
            }
        }
        return result;
    }

    public static void setActiveDiscounts(Set<Discount> activeDiscounts) {
        DiscountManager.activeDiscounts = activeDiscounts;
    }
}