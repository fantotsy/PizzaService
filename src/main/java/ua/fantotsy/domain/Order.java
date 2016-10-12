package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.fantotsy.domain.discounts.Discount;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Component
//@Scope(scopeName = "prototype")
public class Order {
    /*Fields*/
    private Long id;
    private List<Pizza> pizzas;
    private Customer customer;
    private Payment payment;
    private Status status;
    private Set<Discount> activeDiscounts;

    /*Constructors*/
    public Order(Set<Discount> discounts) {
        status = Status.NEW;
        payment = new Payment();
        removeInactiveDiscounts(discounts);
        activeDiscounts = discounts;
    }

    /*Internal Objects*/
    private enum Status {
        NEW {
            public Status nextStatus() {
                return IN_PROGRESS;
            }

            public Status previousStatus() {
                throw new RuntimeException("Previous status for NEW does not exist.");
            }
        },
        IN_PROGRESS {
            public Status nextStatus() {
                return DONE;
            }

            public Status previousStatus() {
                return CANCELED;
            }
        },
        CANCELED {
            public Status nextStatus() {
                throw new RuntimeException("Next status for CANCELED does not exist.");
            }

            public Status previousStatus() {
                throw new RuntimeException("Previous status for CANCELED does not exist.");
            }
        },
        DONE {
            public Status nextStatus() {
                throw new RuntimeException("Next status for DONE does not exist.");
            }

            public Status previousStatus() {
                throw new RuntimeException("Previous status for DONE does not exist.");
            }
        };

        public abstract Status nextStatus();

        public abstract Status previousStatus();
    }

    /*Public cMethods*/
    public int getAmountOfPizzas() {
        return pizzas.size();
    }

    public void countInitialPrice() {
        double result = 0.0;
        for (Pizza pizza : pizzas) {
            result += pizza.getPrice();
        }
        setInitialPrice(result);
    }

    public void countDiscount() {
        double maxDiscount = 0.0;
        for (Discount discount : activeDiscounts) {
            if (discount.canBeApplied(this)) {
                double currentDiscount = discount.getDiscount(this);
                if (currentDiscount > maxDiscount) {
                    maxDiscount = currentDiscount;
                }
            }
        }
        setDiscount(maxDiscount);
    }

    public void countTotalPrice() {
        countInitialPrice();
        countDiscount();
        setTotalPrice(getInitialPrice() - getDiscount());
    }

    public void cancel() {
        status.previousStatus();
    }

    public void confirm() {
        status.nextStatus();
    }

    public void pay() {
        if (customer.hasAccumulativeCard()) {
            customer.increaseAccumulativeCardBalance(getTotalPrice());
        }
        status.nextStatus();
    }

    public boolean isEmpty() {
        return (pizzas.size() == 0);
    }

    /*Private Methods*/
    private void removeInactiveDiscounts(Set<Discount> discounts) {
        for (Discount discount : discounts) {
            if (discount.getState().equals(Discount.DiscountState.INACTIVE)) {
                discounts.remove(discount);
            }
        }
    }

    /*Getters & Setters*/
    public void setActiveDiscounts(Set<Discount> activeDiscounts) {
        this.activeDiscounts = activeDiscounts;
    }

    public Double getInitialPrice() {
        return payment.getInitialPrice();
    }

    public void setInitialPrice(Double initialPrice) {
        payment.setInitialPrice(initialPrice);
    }

    public Double getDiscount() {
        return payment.getDiscount();
    }

    public void setDiscount(Double discount) {
        payment.setDiscount(discount);
    }

    public Double getTotalPrice() {
        return payment.getTotalPrice();
    }

    public void setTotalPrice(Double totalPrice) {
        payment.setTotalPrice(totalPrice);
    }


    public List<Pizza> getOrder() {
        return pizzas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(List<Pizza> order) {
        this.pizzas = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order1 = (Order) o;
        if (id != null ? !id.equals(order1.id) : order1.id != null) return false;
        if (pizzas != null ? !pizzas.equals(order1.pizzas) : order1.pizzas != null) return false;
        return customer != null ? customer.equals(order1.customer) : order1.customer == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pizzas != null ? pizzas.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ua.fantotsy.domain.Order{" +
                "id=" + id +
                ", order=" + pizzas +
                ", customer=" + customer +
                '}';
    }
}