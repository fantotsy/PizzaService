package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class Customer {
    /*Fields*/
    private Long id;
    private String name;
    private Address address;
    private AccumulativeCard accumulativeCard;

    /*Constructors*/
    public Customer() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        return id != null ? id.equals(customer.id) : customer.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "\tName: " + name +
                "\n\tAddress:\n" + address;
    }
}