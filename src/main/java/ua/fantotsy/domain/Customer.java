package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "customers")
@Component
@Scope(scopeName = "prototype")
public class Customer {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "accumulative_card_id")
    private AccumulativeCard accumulativeCard;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    /*Constructors*/
    public Customer() {

    }

    public Customer(String name, Address address, AccumulativeCard accumulativeCard) {
        this.name = name;
        this.address = address;
        this.accumulativeCard = accumulativeCard;
    }

    /*Methods*/
    public double getCardBalance() {
        if (hasAccumulativeCard()) {
            return accumulativeCard.getBalance();
        }
        throw new RuntimeException("This customer has no accumulative card.");
    }

    public boolean hasAccumulativeCard() {
        return (accumulativeCard != null);
    }

    public void increaseAccumulativeCardBalance(double delta) {
        accumulativeCard.increaseAccumulativeCardBalance(delta);
    }

    /*Getters & Setters*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AccumulativeCard getAccumulativeCard() {
        return accumulativeCard;
    }

    public void setAccumulativeCard(AccumulativeCard accumulativeCard) {
        this.accumulativeCard = accumulativeCard;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (address != null ? !address.equals(customer.address) : customer.address != null) return false;
        return accumulativeCard != null ? accumulativeCard.equals(customer.accumulativeCard) : customer.accumulativeCard == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (accumulativeCard != null ? accumulativeCard.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\tName: " + name +
                "\n\tAddress:\n" + address;
    }
}