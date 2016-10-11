package ua.fantotsy.services.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.repository.discount.DiscountRepository;

import java.util.Set;

@Service
public class SimpleDiscountService implements DiscountService {
    /*Fields*/
    private DiscountRepository discountRepository;

    /*Constructors*/
    @Autowired
    public SimpleDiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    /*Methods*/
    @Override
    public Discount getDiscountByName(String name) {
        return discountRepository.getDiscountByName(name);
    }

    @Override
    public void addNewDiscount(Discount discount) {
        discountRepository.addNewDiscount(discount);
    }

    @Override
    public Set<Discount> getDiscounts(){
        return discountRepository.getDiscounts();
    }
}