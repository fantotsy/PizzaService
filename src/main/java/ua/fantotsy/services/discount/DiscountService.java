package ua.fantotsy.services.discount;

import ua.fantotsy.domain.discounts.Discount;

import java.util.Set;

public interface DiscountService {

    Discount getDiscountByName(String name);

    void addNewDiscount(Discount discount);

    Set<Discount> getDiscounts();
}