package ua.fantotsy.domain.discounts;

import org.springframework.stereotype.Component;
import ua.fantotsy.domain.Order;
import ua.fantotsy.infrastructure.utils.Utils;

import javax.persistence.Entity;

@Entity
@Component
public class AccumulativeCardDiscount extends Discount {
    private final static int INITIAL_PRICE_PERCENTAGE_LIMIT = 30;
    private final static int ACCUMULATION_CARD_PERCENTAGE = 10;

    @Override
    public boolean canBeApplied(Order order) {
        this.order = order;
        return isAccumulationCardActivated();
    }

    @Override
    public double getDiscount(Order order) {
        if (isMoreThanPercentageOfTotalPrice()) {
            return getInitialPriceMaxPercentage();
        }
        return getAccumulationCardDiscount();
    }

    private double getAccumulationCardDiscount() {
        return Utils.getPercentageOfNumber(getAccumulationCardBalance(), ACCUMULATION_CARD_PERCENTAGE);
    }

    private boolean isAccumulationCardActivated() {
        return order.getCustomer().hasAccumulativeCard();
    }

    private double getAccumulationCardBalance() {
        return order.getCustomer().getCardBalance();
    }

    private double getInitialPriceMaxPercentage() {
        double initialPrice = getInitialPrice();
        return Utils.getPercentageOfNumber(initialPrice, INITIAL_PRICE_PERCENTAGE_LIMIT);
    }

    private boolean isMoreThanPercentageOfTotalPrice() {
        double initialPricePercentage = getInitialPriceMaxPercentage();
        double accumulationCardPercentage = getAccumulationCardDiscount();
        return (accumulationCardPercentage > initialPricePercentage);
    }

    private double getInitialPrice() {
        return order.getPayment().getInitialPrice();
    }
}