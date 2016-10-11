package ua.fantotsy.repository.discount;

import org.springframework.stereotype.Repository;
import ua.fantotsy.domain.discounts.Discount;

import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemoryDiscountRepository implements DiscountRepository {
    /*Fields*/
    private final Set<Discount> discounts;

    /*Constructors*/
    public InMemoryDiscountRepository() {
        discounts = new HashSet<>();
    }

    /*Methods*/
    @Override
    public Discount getDiscountByName(String name) {
        if (discounts.size() > 0) {
            for (Discount discount : discounts) {
                if (discount.getName().equals(name)) {
                    return discount;
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public void addNewDiscount(Discount discount) {
        discounts.add(discount);
    }

    @Override
    public Set<Discount> getDiscounts(){
        return discounts;
    }

}