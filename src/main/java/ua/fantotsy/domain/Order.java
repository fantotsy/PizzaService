package ua.fantotsy.domain;

import org.hibernate.annotations.*;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;
import ua.fantotsy.domain.discounts.DiscountManager;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
@Component
@Scope(scopeName = "prototype")
@NamedQueries({
        @NamedQuery(name = "Order.findOrdersByCustomerName",
                query = "SELECT o FROM Order o WHERE o.customer.name = :customerName"),
        @NamedQuery(name = "Order.getNumberOfOrders",
                query = "SELECT COUNT(*) FROM Order")
})
public class Order extends ResourceSupport implements Serializable {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long orderId;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pizzas_quantities", joinColumns = @JoinColumn(name = "order_id", nullable = false))
    @MapKeyJoinColumn(name = "pizza_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
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

    /*Constructors*/
    public Order() {
        status = OrderStatus.NEW;
        pizzas = new HashMap<>();
    }

    public Order(Map<Pizza, Integer> pizzas, Customer customer, Address address) {
        this();
        this.pizzas = pizzas;
        this.customer = customer;
        this.address = address;
    }

    /*Internal Objects*/
    public enum OrderStatus {
        NEW {
            @Override
            public OrderStatus nextStatus() {
                return IN_PROGRESS;
            }

            @Override
            public OrderStatus previousStatus() {
                return CANCELED;
            }
        },
        IN_PROGRESS {
            @Override
            public OrderStatus nextStatus() {
                return DONE;
            }

            @Override
            public OrderStatus previousStatus() {
                return NEW;
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

    public void removePizzas(List<Pizza> pizzas) {
        for (Pizza pizza : pizzas) {
            removePizza(pizza);
        }
    }

    public void addPizza(Pizza pizza) {
        if (!status.equals(OrderStatus.NEW)) {
            throw new RuntimeException("Pizza cannot be inserted into 'IN_PROGRESS', 'DONE' or 'CANCELED' order.");
        } else {
            int initialQuantity = 0;
            if (pizzas.containsKey(pizza)) {
                initialQuantity = pizzas.get(pizza);
            }
            pizzas.put(pizza, ++initialQuantity);
        }
    }

    public void removePizza(Pizza pizza) {
        if (!status.equals(OrderStatus.NEW)) {
            throw new RuntimeException("Pizza cannot be removed from 'IN_PROGRESS', 'DONE' or 'CANCELED' order.");
        } else {
            if (!pizzas.containsKey(pizza)) {
                throw new RuntimeException("Such find does not exist in order");
            } else {
                int initialQuantity = pizzas.get(pizza);
                if (initialQuantity > 1) {
                    pizzas.put(pizza, --initialQuantity);
                } else {
                    pizzas.remove(pizza);
                }
            }
        }
    }

    public int amountOfPizzas() {
        int result = 0;
        for (Map.Entry<Pizza, Integer> pizza : pizzas.entrySet()) {
            result += pizza.getValue();
        }
        return result;
    }

    public Pizza pizzaById(Long id) {
        for (Map.Entry<Pizza, Integer> pizza : pizzas.entrySet()) {
            if ((pizza.getKey().getPizzaId()).equals(id)) {
                return pizza.getKey();
            }
        }
        throw new RuntimeException("Pizza not found.");
    }

    public void cancel() {
        status = status.previousStatus();
    }

    public void confirm() {
        status = status.nextStatus();
        payment = new Payment();
        countTotalPrice();
    }

    public void pay() {
        status = status.nextStatus();
        if (customer.hasAccumulativeCard()) {
            customer.increaseAccumulativeCardBalance(payment.getTotalPrice());
        }
        payment.setDateTime(LocalDateTime.now());
    }

    /*Private Methods*/
    private void countInitialPrice() {
        Double result = 0.0;
        for (Map.Entry<Pizza, Integer> pizza : pizzas.entrySet()) {
            result += (pizza.getKey().getPrice() * pizza.getValue());
        }
        payment.setInitialPrice(result);
    }

    private void countDiscount() {
        DiscountManager.countDiscount(this);
    }

    private void countTotalPrice() {
        countInitialPrice();
        countDiscount();
        payment.setTotalPrice(payment.getInitialPrice() - payment.getDiscount());
    }

    /*Getters & Setters*/
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
        info.append("ORDER #" + orderId + ":\n");
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