package ua.fantotsy.domain;

import ua.fantotsy.domain.discounts.Discount;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "pizzas_quantities", joinColumns = @JoinColumn(name = "order_id", nullable = false))
    @MapKeyJoinColumn(name = "pizza_id")
    @Column(name = "quantity", nullable = false)
    private Map<Pizza, Integer> pizzas;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "payment_id")
    private Payment payment;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;
    @Transient
    private Set<Discount> activeDiscounts;

    /*Constructors*/
    public Order() {
        status = OrderStatus.NEW;
        pizzas = new HashMap<>();
        payment = new Payment();
        activeDiscounts = new HashSet<>();
    }

    public Order(Set<Discount> discounts) {
        this();
        removeInactiveDiscounts(discounts);
        activeDiscounts = discounts;
    }

    public Order(Map<Pizza, Integer> pizzas, Customer customer, Set<Discount> activeDiscounts) {
        this();
        this.pizzas = pizzas;
        this.customer = customer;
        this.activeDiscounts = activeDiscounts;
    }

    /*Internal Objects*/
    private enum OrderStatus {
        NEW {
            @Override
            public OrderStatus nextStatus() {
                return IN_PROGRESS;
            }

            @Override
            public OrderStatus previousStatus() {
                throw new RuntimeException("Previous status for NEW does not exist.");
            }
        },
        IN_PROGRESS {
            @Override
            public OrderStatus nextStatus() {
                return DONE;
            }

            @Override
            public OrderStatus previousStatus() {
                return CANCELED;
            }
        },
        CANCELED {
            @Override
            public OrderStatus nextStatus() {
                throw new RuntimeException("Next status for CANCELED does not exist.");
            }

            @Override
            public OrderStatus previousStatus() {
                throw new RuntimeException("Previous status for CANCELED does not exist.");
            }
        },
        DONE {
            @Override
            public OrderStatus nextStatus() {
                throw new RuntimeException("Next status for DONE does not exist.");
            }

            @Override
            public OrderStatus previousStatus() {
                throw new RuntimeException("Previous status for DONE does not exist.");
            }
        };

        public abstract OrderStatus nextStatus();

        public abstract OrderStatus previousStatus();
    }

    /*Public Methods*/
    public void insertPizzas(List<Pizza> pizzas) {
        for (Pizza pizza : pizzas) {
            addPizza(pizza);
        }
    }

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
        for (Map.Entry<Pizza, Integer> pizza : pizzas.entrySet()) {
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
        throw new RuntimeException("Pizza not found.");
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
        countTotalPrice();
        status = status.nextStatus();
    }

    public void pay() {
        if (customer.hasAccumulativeCard()) {
            customer.increaseAccumulativeCardBalance(getTotalPrice());
        }
        status = status.nextStatus();
        setLocalDateTime(LocalDateTime.now());
    }

    public Boolean isEmpty() {
        return (pizzas.size() == 0);
    }

    /*Private Methods*/
    private void countInitialPrice() {
        Double result = 0.0;
        for (Map.Entry<Pizza, Integer> pizza : pizzas.entrySet()) {
            result += (pizza.getKey().getPrice() * pizza.getValue());
        }
        setInitialPrice(result);
    }

    private void countDiscount() {
        Double maxDiscount = 0.0;
        for (Discount discount : activeDiscounts) {
            if (discount.canBeApplied(this)) {
                Double currentDiscount = discount.getDiscount(this);
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

    public void setPizzas(Map<Pizza, Integer> pizzas) {
        this.pizzas = pizzas;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public LocalDateTime getDateTime() {
        return payment.getDateTime();
    }

    private void setLocalDateTime(LocalDateTime dateTime) {
        payment.setDateTime(dateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if (pizzas != null ? !pizzas.equals(order.pizzas) : order.pizzas != null) return false;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
        if (payment != null ? !payment.equals(order.payment) : order.payment != null) return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = pizzas != null ? pizzas.hashCode() : 0;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("ORDER #" + id + ":\n");
        info.append("CUSTOMER:\n" + customer);
        info.append("PIZZAS:\n");
        for (Map.Entry<Pizza, Integer> entry : pizzas.entrySet()) {
            info.append("\t" + entry.getKey().getName() + ": " + entry.getValue() + "x");
            info.append("\n\t----------\n");
        }
        info.append("\n");
        info.append("PAYMENT:\n" + payment);
        return info.toString();
    }
}