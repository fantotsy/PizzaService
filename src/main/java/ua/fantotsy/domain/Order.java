package ua.fantotsy.domain;

import ua.fantotsy.domain.discounts.Discount;

import java.util.List;
import java.util.Set;

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
            @Override
            public Status nextStatus() {
                return IN_PROGRESS;
            }

            @Override
            public Status previousStatus() {
                throw new RuntimeException("Previous status for NEW does not exist.");
            }
        },
        IN_PROGRESS {
            @Override
            public Status nextStatus() {
                return DONE;
            }

            @Override
            public Status previousStatus() {
                return CANCELED;
            }
        },
        CANCELED {
            @Override
            public Status nextStatus() {
                throw new RuntimeException("Next status for CANCELED does not exist.");
            }

            @Override
            public Status previousStatus() {
                throw new RuntimeException("Previous status for CANCELED does not exist.");
            }
        },
        DONE {
            @Override
            public Status nextStatus() {
                throw new RuntimeException("Next status for DONE does not exist.");
            }

            @Override
            public Status previousStatus() {
                throw new RuntimeException("Previous status for DONE does not exist.");
            }
        };

        public abstract Status nextStatus();

        public abstract Status previousStatus();
    }

    /*Public Methods*/
    public int getAmountOfPizzas() {
        return pizzas.size();
    }

    private void countInitialPrice() {
        double result = 0.0;
        for (Pizza pizza : pizzas) {
            result += pizza.getPrice();
        }
        setInitialPrice(result);
    }

    private void countDiscount() {
        double maxDiscount = 0.0;
        for (Discount discount : activeDiscounts) {
            if (discount.canBeApplied(this)) {
                double currentDiscount = discount.getDiscount(this);
                if (currentDiscount > maxDiscount) {
                    maxDiscount = currentDiscount;
                    setAppliedDiscount(discount);
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
        status = status.previousStatus();
    }

    public void confirm() {
        status = status.nextStatus();
    }

    public void pay() {
        if (customer.hasAccumulativeCard()) {
            customer.increaseAccumulativeCardBalance(getTotalPrice());
        }
        status = status.nextStatus();
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
    public Discount getAppliedDiscount() {
        return payment.getAppliedDiscount();
    }

    private void setAppliedDiscount(Discount appliedDiscount) {
        payment.setAppliedDiscount(appliedDiscount);
    }

    public double getInitialPrice() {
        return payment.getInitialPrice();
    }

    private void setInitialPrice(double initialPrice) {
        payment.setInitialPrice(initialPrice);
    }

    private double getDiscount() {
        return payment.getDiscount();
    }

    private void setDiscount(double discount) {
        payment.setDiscount(discount);
    }

    public double getTotalPrice() {
        return payment.getTotalPrice();
    }

    private void setTotalPrice(double totalPrice) {
        payment.setTotalPrice(totalPrice);
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
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

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (pizzas != null ? !pizzas.equals(order.pizzas) : order.pizzas != null) return false;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
        if (payment != null ? !payment.equals(order.payment) : order.payment != null) return false;
        return status == order.status;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pizzas != null ? pizzas.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("Order #" + id + ":\n");
        info.append("Customer:\n" + customer);
        return info.toString();
    }
}