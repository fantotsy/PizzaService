package ua.fantotsy.repository.discount;

import ua.fantotsy.domain.discounts.Discount;

import java.util.Set;

public interface DiscountRepository {

    Discount getDiscountByName(String name);

    void addNewDiscount(Discount discount);

    Set<Discount> getDiscounts();
}