package ua.fantotsy.domain;

import ua.fantotsy.domain.discounts.Discount;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "order")
public class Order {
    /*Fields*/
    @Id
    @GeneratedValue
    private Long id;
    private Map<Pizza, Integer> pizzas;
    @ManyToOne
    private Customer customer;
    @OneToOne
    private Payment payment;
    private Status status;
    private Set<Discount> activeDiscounts;

    /*Constructors*/
    public Order(Set<Discount> discounts) {
        status = Status.NEW;
        pizzas = new HashMap<>();
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
    public void addPizza(Pizza pizza) {
        int initialQuantity = 0;
        if (pizzas.containsKey(pizza)) {
            initialQuantity = pizzas.get(pizza);
        }
        pizzas.put(pizza, ++initialQuantity);
    }

    public void removePizza(Pizza pizza) {
        if (!pizzas.containsKey(pizza)) {
            throw new RuntimeException("Such pizza does not exist in order");
        } else {
            int initialQuantity = pizzas.get(pizza);
            if (initialQuantity > 1) {
                pizzas.put(pizza, --initialQuantity);
            } else {
                pizzas.remove(pizza);
            }
        }
    }

    public int getAmountOfPizzas() {
        int result = 0;
        for(Map.Entry<Pizza, Integer> pizza : pizzas.entrySet()){
            result += pizza.getValue();
        }
        return result;
    }

    public Pizza getPizzaById(Long id) {
        for (Map.Entry<Pizza, Integer> pizza : pizzas.entrySet()) {
            if ((pizza.getKey().getId()).equals(id)) {
                return pizza.getKey();
            }
        }
        throw new RuntimeException("Pizza is not found.");
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
    private void countInitialPrice() {
        double result = 0.0;
        for (Map.Entry<Pizza, Integer> pizza : pizzas.entrySet()) {
            result += (pizza.getKey().getPrice() * pizza.getValue());
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

    private void removeInactiveDiscounts(Set<Discount> discounts) {
        for (Discount discount : discounts) {
            if (discount.getState().equals(Discount.DiscountState.INACTIVE)) {
                discounts.remove(discount);
            }
        }
    }

    /*Getters & Setters*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Pizza, Integer> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        for(Pizza pizza : pizzas){
            addPizza(pizza);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getInitialPrice() {
        return payment.getInitialPrice();
    }

    private void setInitialPrice(double initialPrice) {
        payment.setInitialPrice(initialPrice);
    }

    public Discount getAppliedDiscount() {
        return payment.getAppliedDiscount();
    }

    private void setAppliedDiscount(Discount appliedDiscount) {
        payment.setAppliedDiscount(appliedDiscount);
    }

    public double getDiscount() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return id != null ? id.equals(order.id) : order.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("ORDER #" + id + ":\n");
        info.append("CUSTOMER:\n" + customer);
        info.append("PIZZAS:\n");
//        for (int i = 0; i < pizzas.size(); i++) {
//            info.append(pizzas.get(i));
//            if (i < (pizzas.size() - 1)) {
//                info.append("\n\t----------\n");
//            } else {
//                info.append("\n");
//            }
//        }
        info.append("PAYMENT:\n" + payment);
        return info.toString();
    }
}